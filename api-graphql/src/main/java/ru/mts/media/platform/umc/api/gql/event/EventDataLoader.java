package ru.mts.media.platform.umc.api.gql.event;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsDataLoader;
import lombok.AllArgsConstructor;
import org.dataloader.BatchLoaderWithContext;
import ru.mts.media.platform.umc.domain.event.EventSot;
import ru.mts.media.platform.umc.domain.gql.types.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@DgsComponent
@AllArgsConstructor
public class EventDataLoader {

    private final EventSot eventSot;

    @DgsDataLoader(name = "eventsByVenue")
    public BatchLoaderWithContext<String, List<Event>> getEventsByVenue() {
        return (venueIds, ctx) -> CompletableFuture.supplyAsync(() -> {
            Map<String, List<Event>> byVenue = eventSot.getLastEventsByVenueIds(venueIds);

            return venueIds.stream().map(venueId -> {
                        List<Event> events = byVenue.get(venueId);
                        return events == null ? List.<Event>of() : List.copyOf(events);
                    })
                    .collect(Collectors.toCollection(() -> new ArrayList<>(venueIds.size())));
        });
    }
}

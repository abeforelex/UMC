package ru.mts.media.platform.umc.api.gql.event;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import org.dataloader.DataLoader;
import ru.mts.media.platform.umc.domain.event.EventSot;
import ru.mts.media.platform.umc.domain.gql.types.Event;
import ru.mts.media.platform.umc.domain.gql.types.Venue;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@DgsComponent
@RequiredArgsConstructor
public class EventDgsQuery {

    private final EventSot eventSot;

    @DgsQuery
    public List<Event> events(@InputArgument Optional<Integer> limit) {
        return eventSot.getAllEvents().stream()
                .limit(limit.orElse(100))
                .toList();
    }

    @DgsData(parentType = "Event", field = "venue")
    public CompletableFuture<Venue> venue(DgsDataFetchingEnvironment dataFetchingEnvironment) {
        Event event = dataFetchingEnvironment.getSource();
        if (event == null) {
            throw new RuntimeException("event was not found");
        }
        String venueId = event.getVenue().getId();
        if (venueId == null) {
            return CompletableFuture.completedFuture(null);
        }
        DataLoader<String, Venue> dataLoader = dataFetchingEnvironment.getDataLoader("venues");
        if (dataLoader != null) {
            return dataLoader.load(venueId);
        }
        throw new RuntimeException("venues DataLoader was not found");
    }
}

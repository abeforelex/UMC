package ru.mts.media.platform.umc.api.gql.venue;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsDataLoader;
import lombok.AllArgsConstructor;
import org.dataloader.BatchLoader;
import ru.mts.media.platform.umc.domain.gql.types.Venue;
import ru.mts.media.platform.umc.domain.venue.VenueSot;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


@DgsComponent
@AllArgsConstructor
public class VenueDataLoader {
    private final VenueSot venueSot;

    @DgsDataLoader(name = "venues")
    public BatchLoader<String, Venue> venuesLoader() {
        return referenceIds -> CompletableFuture.supplyAsync(() -> {
            Map<String, Venue> venues = venueSot.getVenuesByReferenceIds(referenceIds);

            return referenceIds.stream()
                    .map(id -> venues.getOrDefault(id, null))
                    .toList();
        });
    }
}

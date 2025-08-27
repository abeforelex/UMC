package ru.mts.media.platform.umc.api.gql.venue;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import org.dataloader.DataLoader;
import ru.mts.media.platform.umc.domain.gql.types.Event;
import ru.mts.media.platform.umc.domain.gql.types.Venue;
import ru.mts.media.platform.umc.domain.venue.VenueSot;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@DgsComponent
@RequiredArgsConstructor
public class VenueDgsQuery {
    private final VenueSot venueSot;

    @DgsQuery
    public Venue venueByReferenceId(@InputArgument String id) {
        return Optional.of(id).flatMap(venueSot::getVenueByReferenceId).orElse(null);
    }

    @DgsQuery
    public List<Venue> venues() {
        return venueSot.getAllVenues();
    }

    @DgsData(parentType = "Venue", field = "events")
    public CompletableFuture<List<Event>> events(DgsDataFetchingEnvironment dfe) {
        Venue venue = dfe.getSource();
        DataLoader<String, List<Event>> dataLoader = dfe.getDataLoader("eventsByVenue");
        return dataLoader.load(venue.getId());
    }
}

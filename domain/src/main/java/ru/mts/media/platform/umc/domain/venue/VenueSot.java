package ru.mts.media.platform.umc.domain.venue;

import ru.mts.media.platform.umc.domain.gql.types.FullExternalId;
import ru.mts.media.platform.umc.domain.gql.types.Venue;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface VenueSot {
    Optional<Venue> getVenueByReferenceId(String id);

    Optional<Venue> getVenueById(FullExternalId externalId);

    Map<String, Venue> getVenuesByReferenceIds(List<String> ids);

    List<Venue> getAllVenues();
}

package ru.mts.media.platform.umc.domain.event;

import ru.mts.media.platform.umc.domain.gql.types.Event;

import java.util.List;
import java.util.Map;

public interface EventSot {
    List<Event> getAllEvents();
    Map<String, List<Event>> getLastEventsByVenueIds(List<String> venueIds);
}

package ru.mts.media.platform.umc.dao.postgres.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.mts.media.platform.umc.domain.event.EventSave;
import ru.mts.media.platform.umc.domain.event.EventSot;
import ru.mts.media.platform.umc.domain.gql.types.Event;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class EventPgDao implements EventSot {
    private final EventPgRepository repository;
    private final EventPgMapper mapper;


    @EventListener
    public void handleEventCreatedEvent(EventSave event) {
        event.unwrap()
                .map(mapper::asEntity)
                .ifPresent(repository::save);
    }

    @Override
    public List<Event> getAllEvents() {
        List<EventPgEntity> entities = repository.findAll();

        return entities.stream()
                .map(mapper::asModel)
                .toList();
    }

    @Override
    public Map<String, List<Event>> getLastEventsByVenueIds(List<String> venueIds) {
        List<EventPgEntity> entities = repository.findRecentEventsByVenueIds(venueIds);

        List<Event> events = entities.stream()
                .map(mapper::asModel)
                .toList();

        return events.stream()
                .collect(Collectors.groupingBy(e -> e.getVenue().getId()));
    }
}

package ru.mts.media.platform.umc.domain.event;

import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import ru.mts.media.platform.umc.domain.gql.types.SaveEventInput;
import ru.mts.media.platform.umc.domain.venue.VenueSot;

@Service
@RequiredArgsConstructor
public class EventDomainService {
    private final ApplicationEventPublisher eventPublisher;
    private final EventDomainServiceMapper eventMapper;
    private final VenueSot venueSot;

    public EventSave save(SaveEventInput input) {
        var event = venueSot.getVenueByReferenceId(input.getVenueReferenceId())
                .map(venue -> eventMapper.save(input, venue))
                .map(EventSave::new)
                .orElseThrow(() -> new DgsEntityNotFoundException("Venue was not found"));

        eventPublisher.publishEvent(event);

        return event;
    }
}

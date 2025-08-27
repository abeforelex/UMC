package ru.mts.media.platform.umc.dao.postgres.venue;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.mts.media.platform.umc.domain.gql.types.FullExternalId;
import ru.mts.media.platform.umc.domain.gql.types.Venue;
import ru.mts.media.platform.umc.domain.venue.VenueSave;
import ru.mts.media.platform.umc.domain.venue.VenueSot;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class VenuePgDao implements VenueSot {
    private final VenuePgRepository repository;
    private final VenuePgMapper mapper;

    public Optional<Venue> getVenueByReferenceId(String id) {
        return Optional.of(id)
                .map(repository::findByReferenceId)
                .map(mapper::asModel);
    }

    @Override
    public Optional<Venue> getVenueById(FullExternalId externalId) {
        Optional.of(externalId)
                .map(mapper::asPk)
                .flatMap(repository::findById);
        return Optional.empty();
    }

    @EventListener
    public void handleVenueCreatedEvent(VenueSave event) {
        event.unwrap()
                .map(mapper::asEntity)
                .ifPresent(repository::save);
    }

    @Override
    public Map<String, Venue> getVenuesByReferenceIds(List<String> ids) {
        return repository.findAllByReferenceIdIn(ids).stream()
                .map(mapper::asModel)
                .collect(Collectors.toMap(
                        Venue::getId,
                        v -> v
                ));
    }

    @Override
    public List<Venue> getAllVenues() {
        return repository.findAll().stream()
                .map(mapper::asModel)
                .toList();
    }
}

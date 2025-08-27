package ru.mts.media.platform.umc.domain.event;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mts.media.platform.umc.domain.gql.types.Event;
import ru.mts.media.platform.umc.domain.gql.types.SaveEventInput;
import ru.mts.media.platform.umc.domain.gql.types.Venue;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
interface EventDomainServiceMapper {
    @Mapping(target = "name", source = "input.name")
    Event save(SaveEventInput input, Venue venue);
}

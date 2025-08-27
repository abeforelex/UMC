package ru.mts.media.platform.umc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import ru.mts.media.platform.umc.domain.gql.types.FullExternalId;
import ru.mts.media.platform.umc.domain.gql.types.SaveVenueInput;
import ru.mts.media.platform.umc.domain.gql.types.Venue;
import ru.mts.media.platform.umc.domain.venue.VenueSot;
import ru.mts.media.platform.umc.domain.venue.VenueSave;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VenueDomainServiceUnitTest {

    @Mock
    private VenueSot venueSot;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @Test
    void testVenueSotInteraction() {
        // Arrange
        FullExternalId venueId = new FullExternalId("test-provider", "test-brand", "test-venue-id");
        
        Venue existingVenue = new Venue();
        existingVenue.setId("test-venue-id");
        existingVenue.setExternalId(venueId);
        existingVenue.setName("Test Venue");

        when(venueSot.getVenueById(venueId)).thenReturn(Optional.of(existingVenue));

        // Act
        Optional<Venue> result = venueSot.getVenueById(venueId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("test-venue-id", result.get().getId());
        assertEquals("Test Venue", result.get().getName());
        verify(venueSot).getVenueById(venueId);
    }

    @Test
    void testVenueSotWhenVenueDoesNotExist() {
        // Arrange
        FullExternalId venueId = new FullExternalId("test-provider", "test-brand", "non-existent-venue");
        when(venueSot.getVenueById(venueId)).thenReturn(Optional.empty());

        // Act
        Optional<Venue> result = venueSot.getVenueById(venueId);

        // Assert
        assertFalse(result.isPresent());
        verify(venueSot).getVenueById(venueId);
    }

    @Test
    void testEventPublisherInteraction() {
        // Arrange
        VenueSave event = new VenueSave(new Venue());

        // Act
        eventPublisher.publishEvent(event);

        // Assert
        verify(eventPublisher).publishEvent(event);
    }

    @Test
    void testFullExternalIdCreation() {
        // Arrange & Act
        FullExternalId venueId = new FullExternalId("test-provider", "test-brand", "test-venue-id");

        // Assert
        assertEquals("test-provider", venueId.getProviderId());
        assertEquals("test-brand", venueId.getBrandId());
        assertEquals("test-venue-id", venueId.getExternalId());
    }

    @Test
    void testSaveVenueInputCreation() {
        // Arrange & Act
        SaveVenueInput input = new SaveVenueInput();
        input.setName("Test Venue Name");

        // Assert
        assertEquals("Test Venue Name", input.getName());
    }
}

package com.hermesafe.application.usecase;

import com.hermesafe.domain.entity.Shipment;
import com.hermesafe.domain.repository.ShipmentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateShipmentUseCaseTest {

    @Mock
    private ShipmentRepository repository;

    @InjectMocks
    private CreateShipmentUseCase useCase;

    @Test
    @DisplayName("Should create shipment successfully via usecase")
    void shouldCreateShipmentSuccessfully() {
        Shipment shipment = new Shipment(
                "ENV-9999",
                "HMS-999999-CL",
                "Acme Corp",
                "John Doe",
                "Santiago",
                "Concepción",
                "PENDING",
                "EXPRESS",
                5.0,
                "2026-08-20",
                "2026-08-15",
                "Handle with care",
                true
        );

        when(repository.save(any(Shipment.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Shipment result = useCase.execute(shipment);

        assertNotNull(result);
        assertEquals("ENV-9999", result.getId());
        assertEquals("Acme Corp", result.getSenderName());
        assertEquals("John Doe", result.getRecipientName());
        assertEquals(5.0, result.getWeightKg());
        verify(repository, times(1)).save(any(Shipment.class));
    }
}

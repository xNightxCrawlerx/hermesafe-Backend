package com.hermesafe.infrastructure.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hermesafe.infrastructure.web.dto.CreateShipmentRequest;
import com.hermesafe.infrastructure.web.dto.UpdateShipmentStatusRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@SuppressWarnings("null")
class ShipmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldGetAllShipments() throws Exception {
        mockMvc.perform(get("/api/shipments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(10))))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].trackingCode").exists());
    }

    @Test
    void shouldGetShipmentById() throws Exception {
        mockMvc.perform(get("/api/shipments/ENV-1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("ENV-1001"))
                .andExpect(jsonPath("$.trackingCode").value("HMS-849201-CL"))
                .andExpect(jsonPath("$.senderName").value("TechSolutions Chile SpA"));
    }

    @Test
    void shouldReturn404ForNonExistentShipment() throws Exception {
        mockMvc.perform(get("/api/shipments/ENV-NON-EXISTENT"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void shouldCreateShipmentSuccessfully() throws Exception {
        CreateShipmentRequest request = new CreateShipmentRequest(
                "HMS-999888-CL",
                "Empresa Test Ltda",
                "Juan Pérez",
                "Santiago",
                "Rancagua",
                "EXPRESS",
                "PENDING",
                5.0,
                "Paquete frágil de prueba"
        );

        mockMvc.perform(post("/api/shipments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.trackingCode").value("HMS-999888-CL"))
                .andExpect(jsonPath("$.senderName").value("Empresa Test Ltda"))
                .andExpect(jsonPath("$.destinationCity").value("Rancagua"))
                .andExpect(jsonPath("$.weightKg").value(5.0));
    }

    @Test
    void shouldRejectShipmentWithInvalidWeight() throws Exception {
        CreateShipmentRequest request = new CreateShipmentRequest(
                "HMS-000111-CL",
                "Empresa Test",
                "Juan Pérez",
                "Santiago",
                "Concepción",
                "STANDARD",
                "PENDING",
                -2.0,
                null
        );

        mockMvc.perform(post("/api/shipments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value(containsString("Weight must be greater than 0")));
    }

    @Test
    void shouldUpdateShipmentStatus() throws Exception {
        UpdateShipmentStatusRequest request = new UpdateShipmentStatusRequest("DELIVERED");

        mockMvc.perform(patch("/api/shipments/ENV-1001/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("ENV-1001"))
                .andExpect(jsonPath("$.status").value("DELIVERED"));
    }

    @Test
    void shouldRejectInvalidStatusUpdate() throws Exception {
        UpdateShipmentStatusRequest request = new UpdateShipmentStatusRequest("UNKNOWN_STATUS");

        mockMvc.perform(patch("/api/shipments/ENV-1001/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400));
    }
}

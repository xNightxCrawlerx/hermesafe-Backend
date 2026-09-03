package com.hermesafe.infrastructure.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hermesafe.domain.entity.InventoryItem;
import com.hermesafe.domain.repository.InventoryRepository;
import com.hermesafe.domain.valueobject.ProductId;
import com.hermesafe.infrastructure.web.dto.CalculateRateRequest;
import com.hermesafe.infrastructure.web.dto.ProcessOrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@SuppressWarnings("null")
class ControllersTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private InventoryRepository inventoryRepository;

    @BeforeEach
    void setUp() {
        ProductId pid = new ProductId("ITEM-100");
        InventoryItem item = new InventoryItem(pid, 20);
        inventoryRepository.save(item);
    }

    @Test
    void shouldProcessOrderSuccessfully() throws Exception {
        ProcessOrderRequest request = new ProcessOrderRequest("ITEM-100", 5);

        mockMvc.perform(post("/api/orders/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.productId").value("ITEM-100"))
                .andExpect(jsonPath("$.remainingStock").value(15));
    }

    @Test
    void shouldRejectOrderWhenInsufficientStock() throws Exception {
        ProcessOrderRequest request = new ProcessOrderRequest("ITEM-100", 100);

        mockMvc.perform(post("/api/orders/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void shouldCalculateShippingRateViaPost() throws Exception {
        CalculateRateRequest request = new CalculateRateRequest(3.0, 50, true);

        mockMvc.perform(post("/api/shipping-rates/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(138.0))
                .andExpect(jsonPath("$.rural").value(true));
    }

    @Test
    void shouldCalculateShippingRateViaGet() throws Exception {
        mockMvc.perform(get("/api/shipping-rates/calculate")
                        .param("weight", "1.5")
                        .param("distance", "20")
                        .param("rural", "false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(100.0));
    }

    @Test
    void shouldGetClosestWarehouses() throws Exception {
        mockMvc.perform(get("/api/routes/closest-warehouses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldCheckCityCoverage() throws Exception {
        mockMvc.perform(get("/api/routes/coverage/Santiago"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Santiago"))
                .andExpect(jsonPath("$.covered").value(true));
    }
}

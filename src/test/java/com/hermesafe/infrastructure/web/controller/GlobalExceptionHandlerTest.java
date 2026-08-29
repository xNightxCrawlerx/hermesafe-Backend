package com.hermesafe.infrastructure.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hermesafe.infrastructure.web.dto.CalculateRateRequest;
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
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturn400WhenNegativeWeightIsProvidedViaPost() throws Exception {
        CalculateRateRequest request = new CalculateRateRequest(-5.0, 50, false);

        mockMvc.perform(post("/shipping-rates/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Weight must be positive :)"))
                .andExpect(jsonPath("$.path").value("/shipping-rates/calculate"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void shouldReturn400WhenNegativeDistanceIsProvidedViaGet() throws Exception {
        mockMvc.perform(get("/shipping-rates/calculate")
                        .param("weight", "10.0")
                        .param("distance", "-20")
                        .param("rural", "false"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Distance cannot be negative"))
                .andExpect(jsonPath("$.path").value("/shipping-rates/calculate"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}

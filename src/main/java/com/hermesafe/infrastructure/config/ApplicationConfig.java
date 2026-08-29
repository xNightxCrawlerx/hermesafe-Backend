package com.hermesafe.infrastructure.config;

import com.hermesafe.application.service.OrderService;
import com.hermesafe.application.usecase.CalculateShippingRateUseCase;
import com.hermesafe.application.usecase.ProcessOrderUseCase;
import com.hermesafe.domain.repository.InventoryRepository;
import com.hermesafe.domain.repository.OrderRepository;
import com.hermesafe.domain.repository.RouteRepository;
import com.hermesafe.domain.repository.WarehouseRepository;
import com.hermesafe.domain.service.RateCalculator;
import com.hermesafe.infrastructure.persistence.InMemoryInventoryRepository;
import com.hermesafe.infrastructure.persistence.InMemoryOrderRepository;
import com.hermesafe.infrastructure.persistence.InMemoryRouteRepository;
import com.hermesafe.infrastructure.persistence.InMemoryWarehouseRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    @ConditionalOnMissingBean
    public OrderRepository orderRepository() {
        return new InMemoryOrderRepository();
    }

    @Bean
    @ConditionalOnMissingBean
    public InventoryRepository inventoryRepository() {
        return new InMemoryInventoryRepository();
    }

    @Bean
    @ConditionalOnMissingBean
    public WarehouseRepository warehouseRepository() {
        return new InMemoryWarehouseRepository();
    }

    @Bean
    @ConditionalOnMissingBean
    public RouteRepository routeRepository() {
        return new InMemoryRouteRepository();
    }

    @Bean
    public RateCalculator rateCalculator() {
        return new RateCalculator();
    }

    @Bean
    public ProcessOrderUseCase processOrderUseCase(InventoryRepository inventoryRepository) {
        return new ProcessOrderUseCase(inventoryRepository);
    }

    @Bean
    public CalculateShippingRateUseCase calculateShippingRateUseCase(RateCalculator rateCalculator) {
        return new CalculateShippingRateUseCase(rateCalculator);
    }

    @Bean
    @ConditionalOnMissingBean
    public com.hermesafe.domain.service.RouteOptimizer routeOptimizer() {
        return new com.hermesafe.domain.service.RouteOptimizer(java.util.Map.of(
                "Santiago", 10,
                "Valparaiso", 30,
                "Concepcion", 50,
                "Antofagasta", 120
        ));
    }

    @Bean
    public com.hermesafe.application.usecase.OptimizeRouteUseCase optimizeRouteUseCase(com.hermesafe.domain.service.RouteOptimizer routeOptimizer) {
        return new com.hermesafe.application.usecase.OptimizeRouteUseCase(routeOptimizer);
    }

    @Bean
    public OrderService orderService(InventoryRepository inventoryRepository) {
        return new OrderService(inventoryRepository);
    }
}

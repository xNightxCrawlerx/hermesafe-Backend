package com.hermesafe.application.usecase;

import com.hermesafe.domain.entity.InventoryItem;
import com.hermesafe.domain.service.RateCalculator;
import com.hermesafe.domain.service.RouteOptimizer;
import com.hermesafe.domain.valueobject.Distance;
import com.hermesafe.domain.valueobject.ProductId;
import com.hermesafe.domain.valueobject.ShippingRate;
import com.hermesafe.domain.valueobject.Weight;
import com.hermesafe.infrastructure.persistence.InMemoryInventoryRepository;
import org.junit.jupiter.api.Test;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UseCasesTest {

    @Test
    void shouldExecuteProcessOrderUseCase() {
        InMemoryInventoryRepository repo = new InMemoryInventoryRepository();
        ProductId pid = new ProductId("P500");
        InventoryItem item = new InventoryItem(pid, 10);
        repo.save(item);

        ProcessOrderUseCase useCase = new ProcessOrderUseCase(repo);
        boolean success = useCase.execute(pid, 4);

        assertTrue(success);
        assertEquals(6, repo.findByProductId(pid).get().getStock());
    }

    @Test
    void shouldCalculateShippingRateUseCase() {
        RateCalculator calculator = new RateCalculator();
        CalculateShippingRateUseCase useCase = new CalculateShippingRateUseCase(calculator);

        Weight weight = new Weight(3.0);
        Distance distance = new Distance(50);
        ShippingRate rate = useCase.execute(weight, distance, true);

        assertEquals(138.0, rate.amount(), 0.001);
    }

    @Test
    void shouldOptimizeRouteUseCase() {
        RouteOptimizer optimizer = new RouteOptimizer(Map.of("Santiago", 10, "Valparaiso", 30));
        OptimizeRouteUseCase useCase = new OptimizeRouteUseCase(optimizer);

        assertTrue(useCase.isCovered("Santiago"));
        assertEquals(2, useCase.execute().size());
    }
}

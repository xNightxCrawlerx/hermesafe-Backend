package com.hermesafe.application.usecase;

import com.hermesafe.domain.service.RouteOptimizer;
import java.util.List;

public class OptimizeRouteUseCase {

    private final RouteOptimizer routeOptimizer;

    public OptimizeRouteUseCase(RouteOptimizer routeOptimizer) {
        this.routeOptimizer = routeOptimizer;
    }

    public List<String> execute() {
        return routeOptimizer.getClosestWarehouses();
    }

    public boolean isCovered(String city) {
        return routeOptimizer.isCityCovered(city);
    }
}

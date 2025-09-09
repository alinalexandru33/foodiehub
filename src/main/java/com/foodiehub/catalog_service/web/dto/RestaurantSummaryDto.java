package com.foodiehub.catalog_service.web.dto;

import java.util.List;

public record RestaurantSummaryDto(
        String id, String name, double lat, double lng,
        List<String> cuisine, Integer avgPrepTimeMinutes, Double rating
) {}

package com.foodiehub.catalog_service.web.mapper;

import com.foodiehub.catalog_service.domain.Restaurant;
import com.foodiehub.catalog_service.web.dto.RestaurantSummaryDto;

public class RestaurantMapper {
    public static RestaurantSummaryDto toSummary(Restaurant r) {
        return new RestaurantSummaryDto(
                r.getId(), r.getName(),
                r.getGeo().getY(), r.getGeo().getX(),
                r.getCuisine(), r.getAvgPrepTimeMinutes(), r.getRating()
        );
    }
}

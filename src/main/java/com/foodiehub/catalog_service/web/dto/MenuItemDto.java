package com.foodiehub.catalog_service.web.dto;

import java.math.BigDecimal;
import java.util.List;

public record MenuItemDto(
        String id, String name, BigDecimal price, List<String> allergens, Boolean available
) {}
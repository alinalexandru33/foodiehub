package com.foodiehub.catalog_service.web.controller;

import com.foodiehub.catalog_service.service.RestaurantSearchService;
import com.foodiehub.catalog_service.web.dto.RestaurantSummaryDto;
import com.foodiehub.catalog_service.web.mapper.RestaurantMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantSearchService service;

    @GetMapping
    public ResponseEntity<?> list(
            @RequestParam String near,           // "lat,lng"
            @RequestParam(defaultValue = "5") double radiusKm,
            @RequestParam(required = false) String cuisine,
            @RequestParam(required = false) Integer etaMax,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        String[] parts = near.split(",");
        double lat = Double.parseDouble(parts[0].trim());
        double lng = Double.parseDouble(parts[1].trim());

        Page<RestaurantSummaryDto> result = service.search(lat, lng, radiusKm, cuisine, etaMax, page, size)
                .map(RestaurantMapper::toSummary);

        return ResponseEntity.ok(result);
    }
}

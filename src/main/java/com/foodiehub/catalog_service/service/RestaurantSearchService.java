package com.foodiehub.catalog_service.service;

import com.foodiehub.catalog_service.domain.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantSearchService {
    private final MongoTemplate mongo;

    public Page<Restaurant> search(
            double lat, double lng,
            Double maxDistanceKm,
            String cuisine,
            Integer maxEtaMinutes,
            int page, int size) {

        PageRequest pageable = PageRequest.of(Math.max(0, page), Math.max(1, size));
        Query q = new Query();

        if (maxDistanceKm != null) {
            GeoJsonPoint location = new GeoJsonPoint(lng, lat); // [lng, lat]
            double maxDistanceMeters = maxDistanceKm * 1000.0;
            q.addCriteria(Criteria.where("geo")
                    .nearSphere(location)
                    .maxDistance(maxDistanceMeters));
        }

        if (cuisine != null && !cuisine.isBlank()) {
            q.addCriteria(Criteria.where("cuisine").is(cuisine.toLowerCase()));
        }

        if (maxEtaMinutes != null) {
            q.addCriteria(Criteria.where("avgPrepTimeMinutes").lte(maxEtaMinutes));
        }

        q.with(pageable);

        List<Restaurant> content = mongo.find(q, Restaurant.class);
        long total = mongo.count(Query.of(q).limit(-1).skip(-1), Restaurant.class);

        return new PageImpl<>(content, pageable, total);
    }
}

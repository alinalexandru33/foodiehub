package com.foodiehub.catalog_service.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("restaurants")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@CompoundIndexes({
    @CompoundIndex(name = "geo_2dsphere", def = "{'geo':'2dsphere'}"),
    @CompoundIndex(name = "cuisine_idx", def = "{'cuisine':1}")
})
public class Restaurant {
    @Id
    private String id;
    private String name;
    private GeoJsonPoint geo;
    private List<String> cuisine;
    private Integer avgPrepTimeMinutes;
    private Double rating;
    private List<OpenHour> openHours;
}

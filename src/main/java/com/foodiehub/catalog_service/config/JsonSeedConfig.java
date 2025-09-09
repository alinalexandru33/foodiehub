package com.foodiehub.catalog_service.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.data.mongodb.core.index.Index;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Configuration
@Profile({"dev"})
@RequiredArgsConstructor
@Slf4j
public class JsonSeedConfig implements org.springframework.boot.CommandLineRunner {

    private final MongoTemplate mongo;

    @Override
    public void run(String... args) throws Exception {
        var db = mongo.getDb();
        var restaurantsColl = db.getCollection("restaurants");
        var menuItemsColl = db.getCollection("menu_items");

        long restaurantsCount = restaurantsColl.countDocuments();
        long menuItemsCount = menuItemsColl.countDocuments();

        if (restaurantsCount > 0 && menuItemsCount > 0) {
            log.info("[seed] Collections already populated (restaurants={}, menu_items={}). Skipping seed.", restaurantsCount, menuItemsCount);
            ensureIndexes();
            return;
        }

        log.info("[seed] Loading NDJSON from classpath: /mongo/restaurants.ndjson, /mongo/menu_items.ndjson");

        // --- restaurants ---
        var restaurantsDocs = readNdjson("/mongo/restaurants.ndjson");
        if (!restaurantsDocs.isEmpty()) {
            restaurantsColl.insertMany(restaurantsDocs);
            log.info("[seed] Inserted {} restaurants", restaurantsDocs.size());
        } else {
            log.warn("[seed] No restaurants found in NDJSON.");
        }

        // --- menu_items ---
        var menuItemsDocs = readNdjson("/mongo/menu_items.ndjson");
        if (!menuItemsDocs.isEmpty()) {
            menuItemsColl.insertMany(menuItemsDocs);
            log.info("[seed] Inserted {} menu_items", menuItemsDocs.size());
        } else {
            log.warn("[seed] No menu items found in NDJSON.");
        }

        ensureIndexes();
        log.info("[seed] DONE.");
    }

    private ArrayList<Document> readNdjson(String classpathLocation) throws Exception {
        var res = new ClassPathResource(classpathLocation);
        if (!res.exists()) {
            log.warn("[seed] Resource not found: {}", classpathLocation);
            return new ArrayList<>();
        }
        try (var in = res.getInputStream();
             var reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {

            var docs = reader.lines()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty() && !line.startsWith("//"))
                    .map(Document::parse)
                    .collect(Collectors.toCollection(ArrayList::new));

            return docs;
        }
    }

    private void ensureIndexes() {
        // restaurants: geo 2dsphere + cuisine
        mongo.indexOps("restaurants").createIndex(
                new GeospatialIndex("geo").typed(GeoSpatialIndexType.GEO_2DSPHERE)
        );
        mongo.indexOps("restaurants").createIndex(
                new Index().on("cuisine", Sort.Direction.ASC)
        );

        // menu_items: restaurantId
        mongo.indexOps("menu_items").createIndex(
                new Index().on("restaurantId", Sort.Direction.ASC)
        );

        log.info("[seed] Indexes created (restaurants.geo 2dsphere, restaurants.cuisine, menu_items.restaurantId).");
    }
}
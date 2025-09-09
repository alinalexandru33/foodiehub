package com.foodiehub.catalog_service.repo;

import com.foodiehub.catalog_service.domain.MenuItem;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MenuItemRepo extends MongoRepository<MenuItem, String> {
    List<MenuItem> findByRestaurantId(String restaurantId);
}

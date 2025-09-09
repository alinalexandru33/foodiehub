package com.foodiehub.catalog_service.repo;

import com.foodiehub.catalog_service.domain.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RestaurantRepo extends MongoRepository<Restaurant, String> {

}

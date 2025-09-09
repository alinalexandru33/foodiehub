package com.foodiehub.catalog_service.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document("menu_items")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuItem {
    @Id
    private String id;
    private String restaurantId;
    private String name;
    private BigDecimal price;
    private List<String> allergens;
    private Boolean available;
}

package com.foodiehub.catalog_service.web.controller;

import com.foodiehub.catalog_service.repo.MenuItemRepo;
import com.foodiehub.catalog_service.web.dto.MenuItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/restaurants")
@RequiredArgsConstructor
public class MenuController {

    private final MenuItemRepo menuRepo;

    @GetMapping("{id}/menu")
    public ResponseEntity<?> menu(@PathVariable String id) {
        var items = menuRepo.findByRestaurantId(id).stream()
                .map(mi -> new MenuItemDto(mi.getId(), mi.getName(), mi.getPrice(), mi.getAllergens(), mi.getAvailable()))
                .toList();
        return ResponseEntity.ok(items);
    }
}

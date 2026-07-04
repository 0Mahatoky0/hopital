package com.groupe25.hopital.data.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.groupe25.hopital.data.service.DataTransform;

@Controller
public class MigrationController {
    
    private DataTransform dataTransform;

    public MigrationController(DataTransform dataTransform) {
        this.dataTransform = dataTransform;
    }
    
    @GetMapping("/migration/amenity")
    public void migrateAmemity() {
        this.dataTransform.createAmanityData();
    }
}

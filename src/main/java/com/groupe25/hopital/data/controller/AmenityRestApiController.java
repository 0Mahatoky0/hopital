package com.groupe25.hopital.data.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupe25.hopital.data.model.AmenityModel;
import com.groupe25.hopital.data.service.AmenityService;

@RestController
@RequestMapping("/api/amenity")
public class AmenityRestApiController {

    @Autowired
    private AmenityService amenityService;

    @GetMapping("/all")
    public List<AmenityModel> getAllAmenity() {
        return amenityService.findAll();
    }
}

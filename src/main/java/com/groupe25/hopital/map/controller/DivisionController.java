package com.groupe25.hopital.map.controller;

import com.groupe25.hopital.map.model.DivisionType;
import com.groupe25.hopital.map.service.DivisionService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/carte")
public class DivisionController {

    private final DivisionService divisionService;

    public DivisionController(DivisionService divisionService) {
        this.divisionService = divisionService;
    }

    // GET /api/carte/division?type=DISTRICT | PROVINCE | COMMUNE
    @GetMapping("/division")
    public Map<String, Object> getDivision(@RequestParam("type") DivisionType type) {
        return divisionService.getGeoJson(type);
    }
}
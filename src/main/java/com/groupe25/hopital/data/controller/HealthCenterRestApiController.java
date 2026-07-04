package com.groupe25.hopital.data.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupe25.hopital.data.dto.HealthCenterDTO;
import com.groupe25.hopital.data.dto.LocationDTO;
import com.groupe25.hopital.data.dto.NearestHealthCenterDTO;
import com.groupe25.hopital.data.service.HealthCenterService;


@RestController
@RequestMapping("/api/healthCenter")
public class HealthCenterRestApiController {

    @Autowired
    private HealthCenterService healthCenterService;

    @GetMapping("/all")
    public List<HealthCenterDTO> getAllHealthCenter() {

        return healthCenterService.getAllHealthCenter();
    }

    @PostMapping("/nearest")
    public NearestHealthCenterDTO getNearestHealthCenter(@RequestBody LocationDTO location) {
        return healthCenterService.getClosestCenter(location);
    }
    

}

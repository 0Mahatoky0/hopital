package com.groupe25.hopital.data.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupe25.hopital.data.model.HealthCenterModel;
import com.groupe25.hopital.data.repository.HealthCenterRepository;

@RestController
public class TestController {
    @Autowired
    private HealthCenterRepository healthCenterRepository;

    @GetMapping("/test/all/helthCenter")
    public List<String> getAll() {
        List<HealthCenterModel> allCenters = this.healthCenterRepository.findAll();
        ArrayList<String> helthCenterModel = new ArrayList<>();

        for (HealthCenterModel center : allCenters) {
            helthCenterModel.add(center.getName() + " -> " + center.getAmenity().getLibelle());
        }
        return helthCenterModel;
    } 
}

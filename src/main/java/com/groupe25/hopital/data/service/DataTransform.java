package com.groupe25.hopital.data.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.groupe25.hopital.data.repository.AmenityRepository;
import com.groupe25.hopital.data.repository.HealthCenterRepository;

import jakarta.transaction.Transactional;

import com.groupe25.hopital.data.model.AmenityModel;
import com.groupe25.hopital.data.model.HealthCenterModel;

@Service
public class DataTransform {

    private HealthCenterRepository healthCenterRepository;
    private AmenityRepository amenityRepository;

    public DataTransform(HealthCenterRepository healthCenterRepository, AmenityRepository amenityRepository) {
        this.amenityRepository = amenityRepository;
        this.healthCenterRepository = healthCenterRepository;
    }

    @Transactional
    public void createAmanityData() {
        HashMap<String, Long> amanitiesLibelleId = new HashMap<>();

        List<String> amanities = this.healthCenterRepository.findDistinctAmenity();
        for (String amenity : amanities) {
            AmenityModel amenityModel =  new AmenityModel(amenity);
            this.amenityRepository.save(amenityModel);
            amanitiesLibelleId.put(amenityModel.getLibelle(),amenityModel.getId());
        }

        // updater les amanity dans les healths center
        List<HealthCenterModel> allHealthCenters = this.healthCenterRepository.findAll();

        for (HealthCenterModel healthCenterModel : allHealthCenters) {
            if (amanitiesLibelleId.containsKey(healthCenterModel.getAmenity())) {
                //mettre l id de l amanity comme valeur
                healthCenterModel.setAmenity(amanitiesLibelleId.get(healthCenterModel.getAmenity()).toString());
            }
        }
    }

    public HealthCenterRepository getHealthCenterRepository() {
        return healthCenterRepository;
    }

    public AmenityRepository getAmenityRepository() {
        return amenityRepository;
    }

}

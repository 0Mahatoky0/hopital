package com.groupe25.hopital.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupe25.hopital.data.model.AmenityModel;
import com.groupe25.hopital.data.repository.AmenityRepository;

@Service
public class AmenityService {

    @Autowired
    private AmenityRepository amenityRepository;

    public List<AmenityModel> findAll() {
        return amenityRepository.findAll();
    }
}

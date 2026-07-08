package com.groupe25.hopital.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.groupe25.hopital.data.model.AmenityModel;

public interface AmenityRepository extends JpaRepository<AmenityModel,Long> {
    
}

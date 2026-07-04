package com.groupe25.hopital.data.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupe25.hopital.data.model.AmenityModel;


@Repository
public interface AmenityRepository extends JpaRepository<AmenityModel,Long> {
    
}

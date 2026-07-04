package com.groupe25.hopital.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.groupe25.hopital.data.model.HealthCenterModel;

@Repository
public interface HealthCenterRepository extends JpaRepository<HealthCenterModel,Long> {
    @Query("SELECT DISTINCT h.amenity FROM HealthCenterModel h")
    List<String> findDistinctAmenity();
}

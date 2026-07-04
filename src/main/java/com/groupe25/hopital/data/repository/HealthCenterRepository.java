package com.groupe25.hopital.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.groupe25.hopital.data.model.HealthCenterModel;

public interface HealthCenterRepository extends JpaRepository<HealthCenterModel, Long> {
    @Query("SELECT DISTINCT h.amenity FROM HealthCenterModel h")
    List<String> findDistinctAmenity();

    @Query(value = """
                SELECT *
                FROM centre_sante
                WHERE ST_DWithin(
                    geom,
                    ST_SetSRID(ST_MakePoint(:lon, :lat), 4326)::geography,
                    :radius
                )
            """, nativeQuery = true)
    List<HealthCenterModel> findNearby(
            @Param("lat") double lat,
            @Param("lon") double lon,
            @Param("radius") double radius);
}

package com.groupe25.hopital.data.mapper;


import org.wololo.geojson.GeoJSON;
import org.wololo.jts2geojson.GeoJSONWriter;

import com.groupe25.hopital.data.dto.HealthCenterDTO;
import com.groupe25.hopital.data.model.HealthCenterModel;

public class HealthCenterMapper {

    private static final GeoJSONWriter writer = new GeoJSONWriter();

    public static HealthCenterDTO toDTO(HealthCenterModel entity) {

        HealthCenterDTO dto = new HealthCenterDTO();

        dto.setGid(entity.getGid());
        dto.setName(entity.getName());
        dto.setAmenity(entity.getAmenity());
        dto.setHealthcare(entity.getHealthcare());
        if (entity.getGeom() != null) {
            GeoJSON geoJSON = writer.write(entity.getGeom());
            dto.setGeometry(geoJSON);
        }

        return dto;
    }
}
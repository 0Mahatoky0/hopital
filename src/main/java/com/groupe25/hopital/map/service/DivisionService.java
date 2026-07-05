package com.groupe25.hopital.map.service;

import com.groupe25.hopital.map.model.*;
import com.groupe25.hopital.map.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.locationtech.jts.io.geojson.GeoJsonWriter;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DivisionService {

    private final ProvinceRepository provinceRepository;
    private final DistrictRepository districtRepository;
    private final CommuneRepository communeRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final GeoJsonWriter geoJsonWriter = new GeoJsonWriter();

    public DivisionService(ProvinceRepository provinceRepository,
                            DistrictRepository districtRepository,
                            CommuneRepository communeRepository) {
        this.provinceRepository = provinceRepository;
        this.districtRepository = districtRepository;
        this.communeRepository = communeRepository;
    }

    public Map<String, Object> getGeoJson(DivisionType type) {
        List<Map<String, Object>> features = new ArrayList<>();

        switch (type) {
            case PROVINCE -> provinceRepository.findAll().forEach(p ->
                    features.add(buildFeature(p.getId(), p.getNom(), null, p.getGeom())));
            case DISTRICT -> districtRepository.findAll().forEach(d ->
                    features.add(buildFeature(d.getId(), d.getNom(), null, d.getGeom())));
            case COMMUNE -> communeRepository.findAll().forEach(c ->
        features.add(buildFeature(c.getId(), c.getNom(), null, c.getGeom())));
        }

        Map<String, Object> featureCollection = new HashMap<>();
        featureCollection.put("type", "FeatureCollection");
        featureCollection.put("features", features);
        return featureCollection;
    }

    private Map<String, Object> buildFeature(Long id, String nom, Long population,
                                              org.locationtech.jts.geom.Geometry geom) {
        Map<String, Object> feature = new HashMap<>();
        feature.put("type", "Feature");

        Map<String, Object> properties = new HashMap<>();
        properties.put("id", id);
        properties.put("nom", nom);
        if (population != null) properties.put("population", population);
        feature.put("properties", properties);

        try {
            String geomJson = geoJsonWriter.write(geom);
            feature.put("geometry", objectMapper.readValue(geomJson, Map.class));
        } catch (Exception e) {
            feature.put("geometry", null);
        }

        return feature;
    }
}
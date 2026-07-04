package com.groupe25.hopital.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupe25.hopital.data.client.OSRMClient;
import com.groupe25.hopital.data.dto.HealthCenterDTO;
import com.groupe25.hopital.data.dto.LocationDTO;
import com.groupe25.hopital.data.dto.NearestHealthCenterDTO;
import com.groupe25.hopital.data.dto.RouteDTO;
import com.groupe25.hopital.data.mapper.HealthCenterMapper;
import com.groupe25.hopital.data.repository.HealthCenterRepository;

@Service
public class HealthCenterService {

    @Autowired
    private HealthCenterRepository repository;

    private static final double SEARCH_RADIUS = 1000;

    @Autowired
    private OSRMClient osrmClient;

    public List<HealthCenterDTO> getAllHealthCenter() {
        return repository.findAll()
                .stream()
                .map(HealthCenterMapper::toDTO)
                .toList();
    }

    public List<HealthCenterDTO> getAllHealthCenterWithinDistance(double lat, double lon, double radius) {
        return repository.findNearby(lat, lon, radius)
                .stream()
                .map(HealthCenterMapper::toDTO)
                .toList();
    }

    public NearestHealthCenterDTO getClosestCenter(LocationDTO location) {
        HealthCenterDTO bestCenter = null;
        RouteDTO bestRoute = null;

        double bestTime = Double.MAX_VALUE;
        List<HealthCenterDTO> all = getAllHealthCenterWithinDistance(location.getLat(),location.getLon(),SEARCH_RADIUS);
        System.out.println("centres trouvés: " + all.size());
        double lat;
        double lon;

        for (HealthCenterDTO center : all) {
            lat = center.getLat();
            lon = center.getLon();
            RouteDTO route = osrmClient.getRoute(location.getLat(), location.getLon(), lat, lon).getRoutes().get(0);
            if (route.getDuration() < bestTime) {
                bestTime = route.getDuration();
                bestCenter = center;
                bestRoute = route;
            }
        }

        return new NearestHealthCenterDTO(bestCenter, bestRoute);
    }
}
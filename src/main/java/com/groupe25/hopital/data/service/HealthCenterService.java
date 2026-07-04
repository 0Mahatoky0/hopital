package com.groupe25.hopital.data.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.groupe25.hopital.data.dto.HealthCenterDTO;
import com.groupe25.hopital.data.mapper.HealthCenterMapper;
import com.groupe25.hopital.data.repository.HealthCenterRepository;

@Service
public class HealthCenterService {

    @Autowired
    private HealthCenterRepository repository;

    public List<HealthCenterDTO> getAllHealthCenter() {
        return repository.findAll()
                .stream()
                .map(HealthCenterMapper::toDTO)
                .toList();
    }
}
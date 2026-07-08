package com.groupe25.hopital.map.controller;

import com.groupe25.hopital.model.CommuneRatioSante;
import com.groupe25.hopital.repository.CommuneRatioSanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/communes")
public class CommuneRatioSanteController {

    @Autowired
    private CommuneRatioSanteRepository repository;

    @GetMapping("/ratios")
    public List<CommuneRatioSante> getAllCommunesAvecRatios() {
        return repository.findAll();
    }
}
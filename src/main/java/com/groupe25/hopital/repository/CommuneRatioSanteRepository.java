package com.groupe25.hopital.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.groupe25.hopital.model.CommuneRatioSante;

@Repository
public interface CommuneRatioSanteRepository extends JpaRepository<CommuneRatioSante, Integer> {
}

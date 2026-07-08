package com.groupe25.hopital.map.repository;

import com.groupe25.hopital.map.model.Commune;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommuneRepository extends JpaRepository<Commune, Long> {}
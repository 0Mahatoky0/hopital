package com.groupe25.hopital.data.model;

import java.util.List;

import com.groupe25.hopital.data.dto.RouteDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OSRMResponse {
    private List<RouteDTO> routes;
    private String code;
}
package com.groupe25.hopital.map.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {

    @GetMapping("/distance")
    public String dist(){
        return "map/feat_distance";
    }

    @GetMapping("/stat_com")
    public String statCom(){
        return "map/feat_stat_com";
    }
    
    @GetMapping("/division")
    public String divs(){
        return "map/feat_division.html";
    }

}

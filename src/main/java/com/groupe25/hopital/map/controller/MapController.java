package com.groupe25.hopital.map.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {

    @GetMapping("/")
    public String index(){
        return "map/index";
    }

}

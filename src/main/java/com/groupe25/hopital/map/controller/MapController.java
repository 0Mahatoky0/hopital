package com.groupe25.hopital.map.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {

    @GetMapping("/")
    public String welcome() {
        return "redirect:/distance";
    }

    @GetMapping("/distance")
    public String index(){
        return "map/feat_distance";
    }

    @GetMapping("/stat_com")
    public String statCom(){
        return "map/feat_stat_com";
    }

}

package com.eco.ecomap.controllers;

import com.eco.ecomap.dtos.CityDto;
import com.eco.ecomap.dtos.EdgeDto;
import com.eco.ecomap.services.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("ecomap/map")
@RequiredArgsConstructor
public class MapController {
    private final MapService mapService;

    @PostMapping
    public ResponseEntity<?> addCity(
            @RequestBody CityDto cityDto
    ){
        mapService.addCity(cityDto.name(), cityDto.edges());
        return ResponseEntity.ok().build();
    }
}

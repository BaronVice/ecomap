package com.eco.ecomap.controllers;

import com.eco.ecomap.dtos.PathRequest;
import com.eco.ecomap.dtos.PointDto;
import com.eco.ecomap.services.path.PathService;
import com.eco.ecomap.services.path.PathType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ecomap/path")
@RequiredArgsConstructor
public class PathController {
    private final PathService pathService;

    @GetMapping
    public ResponseEntity<?> getPath(
            @RequestParam(name = "city") String city,
            @RequestBody PathRequest pathRequest
            ){
        return ResponseEntity.ok(
                pathService.getPath(
                        city,
                        pathRequest.from(),
                        pathRequest.to()
                )
        );
    }
}

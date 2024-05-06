package com.eco.ecomap.controllers;

import com.eco.ecomap.dtos.sensor.SensorDto;
import com.eco.ecomap.services.sensor.SensorService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("ecomap/sensor")
@RequiredArgsConstructor
public class SensorController {
    private final SensorService sensorService;
}

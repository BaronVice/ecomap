package com.eco.ecomap.services.sensor;

import com.eco.ecomap.dtos.sensor.SensorDto;
import com.eco.ecomap.entities.City;
import com.eco.ecomap.entities.Sensor;
import com.eco.ecomap.repositories.CityRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SensorService {
    private final CityRepository cityRepository;

    public void update(City city) {
        SensorDto[] sensors = getSensors();

        city.setSensors(
                Arrays.stream(sensors).filter(s -> {
                    double y = Double.parseDouble(s.location().latitude());
                    double x = Double.parseDouble(s.location().longitude());

                    return city.getLeftBorder() <= x && x <= city.getRightBorder() &&
                            city.getDownBorder() <= y && y <= city.getUpBorder() &&
                            s.sensordatavalues().stream().anyMatch(v -> v.value_type().equals("P2"));
                }).map(s -> {
                    double y = Double.parseDouble(s.location().latitude());
                    double x = Double.parseDouble(s.location().longitude());

                    return Sensor.builder().city(city).x(x).y(y).pm(s.sensordatavalues().stream().filter(
                            v -> v.value_type().equals("P2")).findFirst().get().value()
                    ).build();
                }).collect(Collectors.toList())
        );
    }

    private SensorDto[] getSensors(){
        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://data.sensor.community/static/v2/data.24h.json")
                .build(); // defaults to GET

        try {
            Response response = client.newCall(request).execute();
            return mapper.readValue(response.body().byteStream(), SensorDto[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

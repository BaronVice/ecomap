package com.eco.ecomap.services;

import com.eco.ecomap.dtos.EdgeDto;
import com.eco.ecomap.entities.City;
import com.eco.ecomap.entities.Edge;
import com.eco.ecomap.mappers.EdgeMapper;
import com.eco.ecomap.repositories.CityRepository;
import com.eco.ecomap.services.sensor.SensorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MapService {
    private final EdgeMapper edgeMapper;
    private final CityRepository cityRepository;
    private final SensorService sensorService;

    @Transactional
    public void addCity(
            String name,
            List<EdgeDto> graph
    ){
        City city = new City();
        List<Edge> edges = edgeMapper.toEdgeList(graph);

        double left = 9999, right = -9999, up = -9999, down = 9999;

        // create map of points if it won't be enough for JPA

        for (Edge e : edges) {
            e.setCity(city);

            left = Math.min(Math.min(e.getSourceX(), e.getTargetX()), left);
            right = Math.max(Math.max(e.getSourceX(), e.getTargetX()), right);
            down = Math.min(Math.min(e.getSourceY(), e.getTargetY()), down);
            up = Math.max(Math.max(e.getSourceY(), e.getTargetY()), up);
        }

        city.setName(name);
        city.setLeftBorder(left);
        city.setDownBorder(down);
        city.setRightBorder(right);
        city.setUpBorder(up);
        city.setEdges(edges);

        sensorService.update(city);

        cityRepository.save(city);
    }
}

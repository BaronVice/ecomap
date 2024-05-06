package com.eco.ecomap.dtos.sensor;

import java.util.List;

public record SensorDto(
        Location location,
        List<SensorDataValues> sensordatavalues
) {
}

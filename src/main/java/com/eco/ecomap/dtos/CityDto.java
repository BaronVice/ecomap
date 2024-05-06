package com.eco.ecomap.dtos;

import java.util.List;

public record CityDto(
        String name,
        List<EdgeDto> edges
) {
}

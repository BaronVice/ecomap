package com.eco.ecomap.dtos;

public record PathRequest(
        PointDto from,
        PointDto to
) {
}

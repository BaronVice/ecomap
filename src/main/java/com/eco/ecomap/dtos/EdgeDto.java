package com.eco.ecomap.dtos;

public record EdgeDto(
        double sourceX,
        double sourceY,
        double targetX,
        double targetY,
        double weight
) {

}

package com.eco.ecomap.services.path;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

public record PQPoint (
   double x,
   double y,
   double weight,
   double pm
) {
}

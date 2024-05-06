package com.eco.ecomap.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Edge {
    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "city_id", referencedColumnName = "id"
    )
    private City city;

    private double sourceX;
    private double sourceY;
    private double targetX;
    private double targetY;

    private double weight;
}

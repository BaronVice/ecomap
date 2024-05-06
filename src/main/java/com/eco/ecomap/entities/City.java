package com.eco.ecomap.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.REMOVE;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class City {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    private double leftBorder;
    private double downBorder;
    private double rightBorder;
    private double upBorder;

    @OneToMany(mappedBy = "city", cascade = {REFRESH, MERGE, PERSIST, REMOVE}, fetch = FetchType.EAGER)
    private List<Edge> edges;
    @OneToMany(mappedBy = "city", cascade = {REFRESH, MERGE, PERSIST, REMOVE}, fetch = FetchType.EAGER)
    private List<Sensor> sensors;
}

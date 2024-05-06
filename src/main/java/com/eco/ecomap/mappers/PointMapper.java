package com.eco.ecomap.mappers;

import com.eco.ecomap.dtos.PointDto;
import com.eco.ecomap.entities.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PointMapper {
    PointDto toPointDto(Point Point);
    Point toPoint(PointDto PointDto);
    List<PointDto> toPointDtoList(List<Point> Points);
    List<Point> toPointList(List<PointDto> PointDtoList);
}

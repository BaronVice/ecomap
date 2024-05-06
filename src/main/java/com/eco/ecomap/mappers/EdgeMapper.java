package com.eco.ecomap.mappers;

import com.eco.ecomap.dtos.EdgeDto;
import com.eco.ecomap.entities.Edge;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EdgeMapper {
    EdgeDto toEdgeDto(Edge edge);
    Edge toEdge(EdgeDto edgeDto);
    List<EdgeDto> toEdgeDtoList(List<Edge> edges);
    List<Edge> toEdgeList(List<EdgeDto> edgeDtoList);
}

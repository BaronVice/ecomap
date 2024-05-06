package com.eco.ecomap.services.path;

import com.eco.ecomap.dtos.PointDto;

import java.util.*;

public class Astar {
    public static List<PointDto> findPath(
            Map<PointDto, List<PQPoint>> g,
            PointDto from,
            PointDto to,
            double pmInfluence
    ){
        Queue<PQPoint> pq = new PriorityQueue<>((o1, o2) ->
                (int) (o1.weight() + o1.pm() * pmInfluence - o2.weight() + o2.pm() * pmInfluence)
        );

        Map<PointDto, Boolean> visited = new HashMap<>();
        Map<PointDto, Double> dist = new HashMap<>();
        for(PointDto p : g.keySet()){
            visited.put(p, false);
            dist.put(p, 99999999999.9);
        }

        pq.add(new PQPoint(from.x(), from.y(), 0, 0));
        while (!pq.isEmpty()){
            PQPoint p = pq.poll();
            PointDto pointDto = new PointDto(p.x(), p.y());
            if (visited.get(pointDto)){
                continue;
            }

            visited.put(pointDto, true);
            List<PQPoint> connected = g.get(pointDto);
            for (PQPoint c : connected) {
                // ...
            }
        }
    }

    private static double potential(PQPoint p1, PointDto p2){
        return Math.sqrt((p1.x() - p2.x()) * (p1.x() - p2.x()) + (p1.y() - p2.y()) * (p1.y() - p2.y()));
    }
}

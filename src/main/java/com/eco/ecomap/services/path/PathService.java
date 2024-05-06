package com.eco.ecomap.services.path;

import com.eco.ecomap.dtos.PointDto;
import com.eco.ecomap.entities.City;
import com.eco.ecomap.entities.Edge;
import com.eco.ecomap.entities.Sensor;
import com.eco.ecomap.entities.matrix.Matrix;
import com.eco.ecomap.entities.matrix.MatrixPoint;
import com.eco.ecomap.entities.matrix.QPoint;
import com.eco.ecomap.exceptions.ApplicationException;
import com.eco.ecomap.repositories.CityRepository;
import com.eco.ecomap.utils.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PathService {
    private static final int CONST = 1000;
//    private final Validator validator;
    private final CityRepository cityRepository;

    public List<List<PointDto>> getPath(
            String city,
            PointDto from,
            PointDto to
    ){
        City c = cityRepository.findByName(city).orElseThrow(
                () -> new ApplicationException("Incorrect name or not implemented", HttpStatus.NOT_FOUND)
        );
        if (from.x() < c.getLeftBorder() || from.x() > c.getRightBorder() ||
                from.y() < c.getDownBorder() || from.y() > c.getUpBorder() ||
                to.x() < c.getLeftBorder() || to.x() > c.getRightBorder() ||
                to.y() < c.getDownBorder() || to.y() > c.getUpBorder()
        ){
            throw new ApplicationException("Out of map borders", HttpStatus.BAD_REQUEST);
        }

        Matrix matrix = buildCoverageArea(c);
        Map<PointDto, List<PQPoint>> g = buildG(c, matrix);

        PointDto source = g.keySet().stream().findFirst().get();
        PointDto target = g.keySet().stream().findFirst().get();

        for (PointDto p : g.keySet()){
            if (dist(from, p) < dist(from, source)){
                source = p;
            }
            if (dist(to, p) < dist(to, target)){
                target = p;
            }
        }

        return new ArrayList<>(
                List.of(
                        Astar.findPath(g, source, target, 0),
                        Astar.findPath(g, source, target, 4.0),
                        Astar.findPath(g, source, target, 8.0)
                )
        );
    }

    private double dist(PointDto p1, PointDto p2){
        return Math.sqrt((p1.x() - p2.x()) * (p1.x() - p2.x()) + (p1.y() - p2.y()) * (p1.y() - p2.y()));
    }

    private Map<PointDto, List<PQPoint>> buildG(City c, Matrix matrix) {
        Map<Integer, Double> sensorIdPm = new HashMap<>();
        c.getSensors().forEach(s -> sensorIdPm.put(s.getId(), s.getPm()));

        List<Edge> edges = c.getEdges();
        Map<PointDto, List<PQPoint>> m = new HashMap<>();
        for(Edge edge : edges){
            int sourceX = convertToInt(edge.getSourceX());
            int sourceY = convertToInt(edge.getSourceY());
            int targetX = convertToInt(edge.getTargetX());
            int targetY = convertToInt(edge.getTargetY());

            PointDto p = new PointDto(edge.getSourceX(), edge.getSourceY());
            if (m.containsKey(p)){
                m.get(p).add(new PQPoint(targetX, targetY, edge.getWeight(),
                        sensorIdPm.get(matrix.points()[sourceX][sourceY].getAligned()))
                );
            } else {
                m.put(p, new ArrayList<>(
                        List.of(new PQPoint(targetX, targetY, edge.getWeight(),
                                sensorIdPm.get(matrix.points()[sourceX][sourceY].getAligned())))
                ));
            }
        }
    }

    private Matrix buildCoverageArea(City c){
        Matrix matrix = initMatrix(c);
        Queue<QPoint> q = new LinkedList<>();

        for (Sensor sensor : c.getSensors()){
            int x = convertToInt(sensor.getX()) - convertToInt(c.getLeftBorder());
            int y = convertToInt(sensor.getY()) - convertToInt(c.getDownBorder());

            q.add(new QPoint(x, y, sensor.getId(), -1));
        }
        bfs(matrix, q);

        return matrix;
    }

    private void bfs(Matrix m, Queue<QPoint> q){
        while (! q.isEmpty()){
            QPoint p = q.poll();
            if (p.x() < 0 || p.y() < 0 || p.y() >= m.points().length || p.x() >= m.points()[0].length
                    || m.points()[p.y()][p.x()] != null){
                continue;
            }
            m.points()[p.y()][p.x()] = new MatrixPoint(p.aligned(), p.level()+1);

            q.add(new QPoint(p.x()-1, p.y(), p.aligned(), p.level()+1));
            q.add(new QPoint(p.x()+1, p.y(), p.aligned(), p.level()+1));
            q.add(new QPoint(p.x(), p.y()-1, p.aligned(), p.level()+1));
            q.add(new QPoint(p.x(), p.y()+1, p.aligned(), p.level()+1));
        }
    }

    private Matrix initMatrix(City c){
        int left = convertToInt(c.getLeftBorder());
        int down = convertToInt(c.getDownBorder());
        int right = convertToInt(c.getRightBorder());
        int up = convertToInt(c.getUpBorder());

//        System.out.println(left);
//        System.out.println(down);
//        System.out.println(right);
//        System.out.println(up);

        return new Matrix(new MatrixPoint[up-down+1][right-left+1]);
    }

    private int convertToInt(double val){
        return (int) (val * CONST);
    }

}

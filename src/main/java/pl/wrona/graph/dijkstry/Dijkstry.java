package pl.wrona.graph.dijkstry;

import pl.wrona.graph.Edge;
import pl.wrona.graph.Graph;
import pl.wrona.graph.Vertex;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class Dijkstry {

    private final Graph graph;

    private final Map<Vertex, List<DijkstryVertex>> path;
    private final List<DijkstryVertex> verticesUnvisited;
    private final List<DijkstryVertex> verticesVisited;

    public Dijkstry(final Graph graph) {
        this.graph = graph;

        this.path = graph.getVertices().stream()
                .collect(Collectors.groupingBy(v -> v, Collectors.mapping(Vertex::toDijkstryVertex, Collectors.toList())));

        this.verticesUnvisited = this.path.entrySet().stream()
                .map(k -> k.getValue().get(0))
                .collect(Collectors.toList());

        this.verticesVisited = new ArrayList<>();
    }

    public Map<Vertex, List<DijkstryVertex>> dijkstry(Vertex v1, Vertex v2) {

        DijkstryVertex currentVertex = verticesUnvisited.stream()
                .filter(v -> v.getVertex().equals(v1))
                .findFirst()
                .orElseThrow();

        currentVertex.setDistance(0.0);
        currentVertex.setVisited(true);

        while (nonNull(currentVertex)) {

            for (Edge edge : graph.getEdges(currentVertex.getVertex())) {
                Vertex nextVertex = edge.getV2();
                DijkstryVertex vertex = path.get(nextVertex).get(0);

                double distance = currentVertex.getDistance() + edge.getWeight();

                if (distance < vertex.getDistance()) {
                    vertex.setDistance(distance);
                    vertex.setPreviousVertex(path.get(edge.getV1()).get(0));
                    vertex.setVisited(true);
                }
            }

            verticesVisited.add(currentVertex);
            verticesUnvisited.remove(currentVertex);
            currentVertex = getUnvisitedVertexWithMinDistance();
        }

        return path;
    }

    public DijkstryVertex getUnvisitedVertexWithMinDistance() {
        return this.verticesUnvisited.stream()
                .min(Comparator.comparing(DijkstryVertex::getDistance))
                .orElse(null);
    }
}

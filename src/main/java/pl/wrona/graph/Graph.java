package pl.wrona.graph;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.wrona.graph.dijkstry.Dijkstry;
import pl.wrona.graph.dijkstry.DijkstryPath;
import pl.wrona.graph.dijkstry.DijkstryVertex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class Graph {

    @Getter
    private final List<Vertex> vertices;
    private final Map<Vertex, Set<Edge>> edges;

    @Getter
    @Builder
    public static class GraphPair {
        private Vertex vertex;
        private Edge edge;
    }

    public Set<Edge> getEdges(Vertex vertex) {
        return edges.get(vertex);
    }

    public Graph(List<Edge> edges) {
        this(edges, true);
    }

    public Graph(List<Edge> edges, boolean reverted) {
        this.edges = edges.stream()
                .map(edge -> reverted ? List.of(edge, edge.revert()) : List.of(edge))
                .flatMap(Collection::stream)
                .map(edge -> GraphPair.builder().vertex(edge.getV1()).edge(edge).build())
                .collect(Collectors.groupingBy(GraphPair::getVertex, Collectors.mapping(GraphPair::getEdge, Collectors.toSet())));

        this.vertices = edges.stream()
                .map(edge -> List.of(edge.getV1(), edge.getV2()))
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    public DijkstryPath dijkstry(Vertex v1, Vertex v2) {
        Dijkstry dijkstry = new Dijkstry(this);

        Map<Vertex, List<DijkstryVertex>> path = dijkstry.dijkstry(v1, v2);

        LinkedList<Vertex> shortestPath = new LinkedList<>();

        DijkstryVertex vertex = path.get(v2).get(0);

        while (Objects.nonNull(vertex)) {
            shortestPath.addFirst(vertex.getVertex());
            vertex = vertex.getPreviousVertex();
        }

        return DijkstryPath.builder()
                .v1(v1)
                .v2(v2)
                .distance(path.get(v2).get(0).getDistance())
                .path(shortestPath)
                .build();
    }

}

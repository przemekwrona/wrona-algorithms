package pl.wrona.graph.dijkstry;

import lombok.Builder;
import lombok.Getter;
import pl.wrona.graph.Vertex;

import java.util.List;

@Getter
@Builder
public class DijkstryPath {

    private Vertex v1;
    private Vertex v2;
    private double distance;
    private List<Vertex> path;
}

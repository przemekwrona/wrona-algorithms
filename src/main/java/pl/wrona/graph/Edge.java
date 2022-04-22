package pl.wrona.graph;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = {"v1", "v2"})
public final class Edge {

    private final Vertex v1;
    private final Vertex v2;
    private final double weight;

    public Edge(Vertex v1, Vertex v2) {
        this(v1, v2, 1.0);
    }

    public Edge revert() {
        return new Edge(v2, v1);
    }
}

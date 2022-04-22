package pl.wrona.graph;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import pl.wrona.graph.dijkstry.DijkstryVertex;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = {"name"})
public final class Vertex {

    private final String name;

    public DijkstryVertex toDijkstryVertex() {
        return new DijkstryVertex(this, Double.MAX_VALUE, false, null);
    }


}

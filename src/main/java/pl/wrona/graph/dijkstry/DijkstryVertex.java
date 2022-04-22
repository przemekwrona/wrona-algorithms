package pl.wrona.graph.dijkstry;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pl.wrona.graph.Vertex;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = {"vertex"})
public final class DijkstryVertex {

    private final Vertex vertex;

    @Setter
    private double distance;

    @Setter
    private boolean visited;

    @Setter
    private DijkstryVertex previousVertex;

}

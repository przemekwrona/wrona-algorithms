package pl.wrona.graph

import pl.wrona.graph.dijkstry.DijkstryPath
import spock.lang.Specification

class GraphTest extends Specification {

    def "should init graph"() {
        given:
        List<Edge> edges = [
                new Edge(new Vertex("1"), new Vertex("2")),
                new Edge(new Vertex("1"), new Vertex("3")),
                new Edge(new Vertex("2"), new Vertex("3")),
                new Edge(new Vertex("3"), new Vertex("4"))
        ]

        when:
        Graph graph = new Graph(edges)
        List<Vertex> vertices = graph.getVertices();

        then:
        vertices.size() == 4
        vertices.containsAll(new Vertex("1"), new Vertex("2"), new Vertex("3"), new Vertex("4"))
    }

    def "should find graph"() {
        given:
        List<Edge> edges = [
                new Edge(new Vertex("1"), new Vertex("2")),
                new Edge(new Vertex("1"), new Vertex("3")),
                new Edge(new Vertex("2"), new Vertex("3")),
                new Edge(new Vertex("3"), new Vertex("4"))
        ]

        and:
        Graph graph = new Graph(edges)

        when:
        DijkstryPath path = graph.dijkstry(new Vertex("2"), new Vertex("4"))

        then:
        path.getV1() == new Vertex("2")
        path.getV2() == new Vertex("4")
        path.getDistance() == 2.0d
        !path.getPath().isEmpty()
        path.getPath().size() == 3
        path.getPath().get(0) == new Vertex("2")
        path.getPath().get(1) == new Vertex("3")
        path.getPath().get(2) == new Vertex("4")
    }
}

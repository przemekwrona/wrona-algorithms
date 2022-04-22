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

    def "should find the shortest path in graph if weights are equal 1.0"() {
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

    def "should find the shortest path in graph if weights are different"() {
        given:
        List<Edge> edges = [
                new Edge(new Vertex("A"), new Vertex("B"), 6),
                new Edge(new Vertex("A"), new Vertex("D"), 1),
                new Edge(new Vertex("B"), new Vertex("C"), 5),
                new Edge(new Vertex("B"), new Vertex("D"), 2),
                new Edge(new Vertex("B"), new Vertex("E"), 2),
                new Edge(new Vertex("C"), new Vertex("E"), 5),
                new Edge(new Vertex("D"), new Vertex("E"), 1)
        ]

        and:
        Graph graph = new Graph(edges)

        when:
        DijkstryPath pathAB = graph.dijkstry(new Vertex("A"), new Vertex("B"))
        DijkstryPath pathAC = graph.dijkstry(new Vertex("A"), new Vertex("C"))

        then:
        pathAB.getV1() == new Vertex("A")
        pathAB.getV2() == new Vertex("B")
        pathAB.getDistance() == 3.0d
        !pathAB.getPath().isEmpty()
        pathAB.getPath().size() == 3
        pathAB.getPath().get(0) == new Vertex("A")
        pathAB.getPath().get(1) == new Vertex("D")
        pathAB.getPath().get(2) == new Vertex("B")

        pathAC.getV1() == new Vertex("A")
        pathAC.getV2() == new Vertex("C")
        pathAC.getDistance() == 7.0d
        !pathAC.getPath().isEmpty()
        pathAC.getPath().size() == 4
        pathAC.getPath().get(0) == new Vertex("A")
        pathAC.getPath().get(1) == new Vertex("D")
        pathAC.getPath().get(2) == new Vertex("E")
        pathAC.getPath().get(3) == new Vertex("C")
    }
}

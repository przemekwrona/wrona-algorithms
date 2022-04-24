package pl.wrona.ai

import pl.wrona.ai.function.ConstantFunction
import pl.wrona.ai.function.LinearFunction
import pl.wrona.ai.function.SigmoidFunction
import spock.lang.Specification

import java.util.stream.IntStream

class NeuralNetworkTest extends Specification {

    def "should init neural network"() {
        given:
        NeuralNetwork network = new NeuralNetwork()

        and:
        double[] vector = new double[]{2.0, 3.0}
        double[] destination = new double[]{5.0}

        and:
        network.addInputLayer(2)
        network.addLayer(3, new SigmoidFunction())
        network.addLayer(1, new LinearFunction(1, 50))

        when:
        network.learn(new double[]{10.0, 20.0}, new double[]{30.0})
        network.learn(new double[]{10.0, 20.0}, new double[]{30.0})
        network.learn(new double[]{10.0, 20.0}, new double[]{30.0})
        network.learn(new double[]{10.0, 20.0}, new double[]{30.0})
        network.learn(new double[]{10.0, 20.0}, new double[]{30.0})
//        IntStream.range(0, 1000)
//                .forEach(l -> {
//                    double x1 = new Random().nextInt(100)
//                    double x2 = new Random().nextInt(100)
//
//                    network.learn(new double[]{x1, x2}, new double[]{x1 + x2})
//                })

        then:
        1 == 1
    }
}

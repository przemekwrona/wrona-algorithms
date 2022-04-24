package pl.wrona.ai;

import lombok.Getter;
import lombok.Setter;
import pl.wrona.ai.function.ActivationFunction;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Neuron {

    @Getter
    private List<Connection> connections;

//    @Getter
//    @Setter
//    private double value;

    @Getter
    @Setter
    private double sum;

    @Getter
    @Setter
    private double delta;


    private ActivationFunction activationFunction;

    public Neuron(ActivationFunction activationFunction) {
        this.connections = List.of();
        this.activationFunction = activationFunction;
    }

    public void addConnections(Layer layer) {
        this.connections = layer.getNeurons().stream()
                .map(Connection::new)
                .collect(Collectors.toList());
    }

    public void propagation() {
        this.delta = 0.0;
        this.sum = connections.stream()
                .map(connection -> connection.getWeight() * connection.getPreviousNeuron().sumActivation())
                .reduce(Double::sum)
                .orElse(0.0d);
    }

    public void addDelta(double delta) {
        this.setDelta(this.getDelta() + delta);
    }

    public double sumActivation() {
        return Objects.nonNull(activationFunction) ? activationFunction.f(sum) : sum;
    }

    public double sumDerivative() {
        return activationFunction.derivative(sum);
    }
}

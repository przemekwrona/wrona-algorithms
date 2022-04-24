package pl.wrona.ai;

import lombok.Getter;
import pl.wrona.ai.function.ActivationFunction;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Layer {

    @Getter
    private List<Neuron> neurons;

    private Layer nextLayer;

    private ActivationFunction activationFunction;

    public Layer(int neurons, ActivationFunction activationFunction) {
        this.activationFunction = activationFunction;
        this.neurons = IntStream.range(0, neurons)
                .mapToObj(i -> new Neuron(activationFunction))
                .collect(Collectors.toList());
    }

    public void joinLayer(Layer layer) {
        nextLayer = layer;

        layer.getNeurons().forEach(neuron -> neuron.addConnections(this));
    }

    public void propagation() {
        getNeurons().forEach(Neuron::propagation);
    }

    public void backpropagation() {
        this.neurons.forEach(neuron -> neuron.getConnections()
                .forEach(connection -> connection.getPreviousNeuron().addDelta(neuron.getDelta() * connection.getWeight() * neuron.sumDerivative())));
    }

    public void updateWeights() {
        this.neurons.forEach(neuron -> neuron.getConnections()
                .forEach(connection -> connection.setWeight(connection.getWeight()
                        + .1 * neuron.getDelta() * connection.getPreviousNeuron().getSum())));
    }


}

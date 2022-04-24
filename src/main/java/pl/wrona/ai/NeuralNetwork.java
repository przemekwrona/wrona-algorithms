package pl.wrona.ai;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import pl.wrona.ai.function.ActivationFunction;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.IntStream;

@Builder
@AllArgsConstructor
public class NeuralNetwork {

    private Layer innerLayer;
    private Layer outputLayer;

    private LinkedList<Layer> layers;

    public NeuralNetwork() {
        this.layers = new LinkedList<>();
    }

    public void addInputLayer(int neurons) {
        Layer layer = new Layer(neurons, null);
        innerLayer = layer;
        outputLayer = layer;
        layers.add(layer);
    }

    public void addLayer(int neurons, ActivationFunction activationFunction) {
        Layer layer = new Layer(neurons, activationFunction);

        if (layers.isEmpty()) {
            innerLayer = layer;
            outputLayer = layer;
            layers.add(layer);
            return;
        }

        outputLayer.joinLayer(layer);
        outputLayer = layer;
        layers.add(layer);
    }

    public double[] learn(double[] vector, double[] destination) {
        double[] output = propagation(vector);

        double[] delta = IntStream.range(0, output.length)
                .mapToDouble(i -> output[i] - destination[i])
                .toArray();

        double errorTotal = Arrays.stream(delta).map(v -> Math.pow(v, 2)).sum() / 2;

        System.out.println(errorTotal);

        backpropagation(delta);

        return output;
    }

    private void backpropagation(double[] delta) {
        int i = 0;
        for (Neuron neuron : outputLayer.getNeurons()) {
            neuron.setDelta(delta[i]);
            i++;
        }

        layers.stream().unordered()
                .filter(layer -> !layer.equals(innerLayer))
                .forEach(Layer::backpropagation);

        layers.stream()
                .filter(layer -> !layer.equals(innerLayer))
                .forEach(Layer::updateWeights);

        System.out.println("");
    }

    private double[] propagation(double[] vector) {

        int index = 0;
        for (Neuron neuron : innerLayer.getNeurons()) {
            neuron.setSum(vector[index]);
            index++;
        }

        layers.stream()
                .filter(layer -> !layer.equals(innerLayer))
                .forEach(Layer::propagation);

        return outputLayer.getNeurons().stream().map(Neuron::sumActivation).mapToDouble(d -> d).toArray();
    }

}


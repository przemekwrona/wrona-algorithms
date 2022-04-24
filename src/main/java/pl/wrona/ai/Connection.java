package pl.wrona.ai;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Connection {

    private final Neuron previousNeuron;

    @Setter
    private double weight;

    public Connection(Neuron neuron) {
        this.previousNeuron = neuron;
//        this.weight = Math.random();
        this.weight = 0.5;
    }

}

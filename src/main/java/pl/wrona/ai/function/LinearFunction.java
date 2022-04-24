package pl.wrona.ai.function;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class LinearFunction implements ActivationFunction {

    private double a;
    private double b;

    @Override
    public double f(double x) {
        return a * x + b;
    }

    @Override
    public double derivative(double x) {
        return a;
    }
}

package pl.wrona.ai.function;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class ConstantFunction implements ActivationFunction {

    private double b;

    @Override
    public double f(double x) {
        return b;
    }

    @Override
    public double derivative(double x) {
        return 0;
    }
}

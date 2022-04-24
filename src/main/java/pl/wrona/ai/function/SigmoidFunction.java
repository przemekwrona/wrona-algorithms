package pl.wrona.ai.function;

import static java.lang.Math.*;

public class SigmoidFunction implements ActivationFunction {

    @Override
    public double f(double x) {
        return 1 / (1 + pow(E, -x));
    }

    @Override
    public double derivative(double x) {
        return x * (1 - x);
    }
}

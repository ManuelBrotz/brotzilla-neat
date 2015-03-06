package ch.brotzilla.neat.math;

public class GaussianFunction extends ExtendedActivationFunction {

    public GaussianFunction() {
        super("neat.math.extended.gaussian", "Gaussian", "Returns the gaussian of the argument.");
    }

    @Override
    protected double _compute(double activation, double[] synapses) {
        return Math.exp(-activation * activation) * 2.0d - 1.0d;
    }

}

package ch.brotzilla.neat.math;

public class ExtendedGaussianFunction extends ExtendedActivationFunction {

    public ExtendedGaussianFunction() {
        super("neat.math.extended.gaussian", "Gaussian", "Returns the gaussian of the argument.");
        setSynapseDefault(0, 2.5);
    }

    @Override
    protected double _compute(double activation, double[] synapses) {
        return Math.exp(-activation * activation);
    }

}

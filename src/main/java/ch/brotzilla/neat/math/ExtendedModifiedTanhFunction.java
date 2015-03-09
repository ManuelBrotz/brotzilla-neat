package ch.brotzilla.neat.math;

public class ExtendedModifiedTanhFunction extends ExtendedActivationFunction {

    public ExtendedModifiedTanhFunction() {
        super("neat.math.extended.modified-tanh", "Modified Hyperbolic Tangent", "Returns the hyperbolic tangent of the argument. The argument will first be raised to the power of 3.");
        setSynapseDefault(0, 1.5);
    }

    @Override
    protected double _compute(double activation, double[] synapses) {
        final double a = activation * activation * activation;
        return 2.0d / (1.0d + Math.exp(-2.0d * a)) - 1.0d;
    }

}

package ch.brotzilla.neat.math;

public class ExtendedTanhFunction extends ExtendedActivationFunction {

    public ExtendedTanhFunction() {
        super("neat.math.extended.tanh", "Hyperbolic Tangent", "Returns the hyperbolic tangent of the argument.");
        setSynapseDefault(0, 2.5);
    }

    @Override
    protected double _compute(double activation, double[] synapses) {
        return 2.0d / (1.0d + Math.exp(-2.0d * activation)) - 1.0d;
    }

}

package ch.brotzilla.neat.math;

public class ExtendedModifiedElliottFunction extends ExtendedActivationFunction {

    public ExtendedModifiedElliottFunction() {
        super("neat.math.extended.modified-elliott", "Modified Elliott", "The elliott function is higher-speed approximation of the hyperbolic tangent and sigmoid functions. The argument will first be raised to the power of 3.");
        setSynapseDefault(0, 2.5);
        setSynapseDefault(1, 2.0);
    }

    @Override
    protected double _compute(double activation, double[] synapses) {
        final double a = activation < 0.0 ? -activation : activation;
        final double b = a * a * a;
        return activation / (1.0 + b);
    }

}

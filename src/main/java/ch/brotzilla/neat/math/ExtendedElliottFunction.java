package ch.brotzilla.neat.math;

public class ExtendedElliottFunction extends ExtendedActivationFunction {

    public ExtendedElliottFunction() {
        super("neat.math.extended.elliott", "Elliott", "The elliott function is higher-speed approximation of the hyperbolic tangent and sigmoid functions.");
    }

    @Override
    protected double _compute(double activation, double[] synapses) {
        final double b = activation < 0.0 ? -activation : activation;
        return activation / (1.0 + b);
    }

}

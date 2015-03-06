package ch.brotzilla.neat.math;

public class LinearFunction extends ExtendedActivationFunction {

    public LinearFunction() {
        super("neat.math.extended.linear", "Linear", "Implements the linear function.");
    }

    @Override
    protected double _compute(double activation, double[] synapses) {
        return activation;
    }

}

package ch.brotzilla.neat.math;

public class ExtendedLinearFunction extends ExtendedActivationFunction {

    public ExtendedLinearFunction() {
        super("neat.math.extended.linear", "Linear", "Implements the linear function.");
    }

    @Override
    protected double _compute(double activation, double[] synapses) {
        return activation;
    }

}

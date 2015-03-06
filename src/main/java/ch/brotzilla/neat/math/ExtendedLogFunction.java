package ch.brotzilla.neat.math;

public class ExtendedLogFunction extends ExtendedActivationFunction {

    public ExtendedLogFunction() {
        super("neat.math.extended.log", "Logarithm", "Returns the logarithm of the argument. Negative input values are mapped to negative output values.");
    }

    @Override
    protected double _compute(double activation, double[] synapses) {
        if (activation < 0) {
            return -Math.log(-activation);
        }
        return Math.log(activation);
    }

}

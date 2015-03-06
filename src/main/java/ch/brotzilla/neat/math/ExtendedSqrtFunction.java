package ch.brotzilla.neat.math;

public class ExtendedSqrtFunction extends ExtendedActivationFunction {

    public ExtendedSqrtFunction() {
        super("neat.math.extended.sqrt", "Square Root", "Returns the positive square root of the argument. Negative input values are mapped to negative output values.");
    }

    @Override
    protected double _compute(double activation, double[] synapses) {
        if (activation < 0) {
            return -Math.sqrt(-activation);
        }
        return Math.sqrt(activation);
    }

}

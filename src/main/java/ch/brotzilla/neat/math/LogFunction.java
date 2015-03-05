package ch.brotzilla.neat.math;

public class LogFunction extends ExtendedActivationFunction {

    public LogFunction() {
        super("neat.math.extended.log", "Logarithm", "Returns the logarithm of the argument. Negative input values are mapped to negative output values.");
    }

    @Override
    protected double _compute(double activation, double[] parameters) {
        if (activation < 0) {
            return -Math.log(-activation);
        }
        return Math.log(activation);
    }

}

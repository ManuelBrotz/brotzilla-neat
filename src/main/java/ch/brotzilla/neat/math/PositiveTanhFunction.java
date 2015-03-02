package ch.brotzilla.neat.math;

public class PositiveTanhFunction extends ActivationFunction {

    public PositiveTanhFunction() {
        super("neat.math.positive.tanh(x, i)", "Positive Hyperbolic Tangent", "Returns the hyperbolic tangent of the argument.", 
                0.0d, 1.0d, 1,
                new String[] {"i"}, new String[] {"Input scaling"},
                new double[] {1.0d}, new double[] {Double.NEGATIVE_INFINITY}, new double[] {Double.POSITIVE_INFINITY});
    }

    @Override
    protected double _compute(double activation, double[] parameters) {
        // TODO Hyperbolic Tangent muss überprüft werden. Auch input scaling.
        return 1.0d / (1.0d + Math.exp(-2.0d * activation * parameters[0]));
    }

}

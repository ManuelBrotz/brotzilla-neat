package ch.brotzilla.neat.math;

public class PositiveCosFunction extends ActivationFunction {

    public PositiveCosFunction() {
        super("neat.math.positive.cos(x, i)", "Positive Cosine", "Returns the trigonometric cosine of an angle in radians.", 
                0.0d, 1.0d, 1,
                new String[] {"i"}, new String[] {"Input scaling"},
                new double[] {1.0d}, new double[] {Double.NEGATIVE_INFINITY}, new double[] {Double.POSITIVE_INFINITY});
    }

    @Override
    protected double _compute(double activation, double[] parameters) {
        return Math.cos(activation * parameters[0]) * 0.5d + 0.5d;
    }

}

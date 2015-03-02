package ch.brotzilla.neat.math;

public class PositiveGaussianFunction extends ActivationFunction {

    public PositiveGaussianFunction() {
        super("neat.math.positive.gaussian(x, i)", "Positive Gaussian", "Computes the gaussian function for the argument.", 
                0.0d, 1.0d, 1,
                new String[] {"i"}, new String[] {"Input scaling"},
                new double[] {1.0d}, new double[] {Double.NEGATIVE_INFINITY}, new double[] {Double.POSITIVE_INFINITY});
    }

    @Override
    protected double _compute(double activation, double[] parameters) {
        // TODO Input scaling muss angepasst werden
        final double p = parameters[0];
        return Math.exp(-activation * activation * p * p);
    }

}

package ch.brotzilla.neat.math;

public class SymmetricGaussianFunction extends ActivationFunction {

    public SymmetricGaussianFunction() {
        super("neat.math.symmetric.gaussian(x, i)", "Symmetric Gaussian", "Computes the gaussian function for the argument.", 
                -1.0d, 1.0d, 1,
                new String[] {"i"}, new String[] {"Input scaling"},
                new double[] {2.5d}, new double[] {Double.NEGATIVE_INFINITY}, new double[] {Double.POSITIVE_INFINITY});
    }

    @Override
    protected double _compute(double activation, double[] parameters) {
        // TODO Input scaling muss angepasst werden
        final double p = parameters[0];
        return Math.exp(-activation * activation * p * p) * 2.0d - 1.0d;
    }

}

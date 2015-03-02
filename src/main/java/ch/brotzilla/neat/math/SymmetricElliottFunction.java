package ch.brotzilla.neat.math;

public class SymmetricElliottFunction extends ActivationFunction {

    public SymmetricElliottFunction() {
        super("neat.math.symmetric.elliott(x, i)", "Symmetric Elliott", "The elliott function is higher-speed approximation of the hyperbolic tangent and sigmoid functions.", 
                -1.0d, 1.0d, 1,
                new String[] {"i"}, new String[] {"Input scaling"},
                new double[] {1.0d}, new double[] {Double.NEGATIVE_INFINITY}, new double[] {Double.POSITIVE_INFINITY});
    }

    @Override
    protected double _compute(double activation, double[] parameters) {
        final double a = activation * parameters[0];
        final double b = a < 0.0d ? -a : a;
        return a / (1.0d + b);
    }

}

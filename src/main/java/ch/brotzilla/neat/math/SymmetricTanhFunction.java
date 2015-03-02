package ch.brotzilla.neat.math;

public class SymmetricTanhFunction extends ActivationFunction {

    public SymmetricTanhFunction() {
        super("neat.math.symmetric.tanh(x, i)", "Symmetric Hyperbolic Tangent", "Returns the hyperbolic tangent of the argument.", 
                -1.0d, 1.0d, 1,
                new String[] {"i"}, new String[] {"Input scaling"},
                new double[] {1.0d}, new double[] {Double.NEGATIVE_INFINITY}, new double[] {Double.POSITIVE_INFINITY});
    }

    @Override
    protected double _compute(double activation, double[] parameters) {
        // TODO Hyperbolic Tangent muss überprüft werden. Auch input scaling.
        return 2.0d / (1.0d + Math.exp(-2.0d * activation * parameters[0])) - 1.0d;
    }

}

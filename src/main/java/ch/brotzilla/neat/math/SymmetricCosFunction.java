package ch.brotzilla.neat.math;

public class SymmetricCosFunction extends ActivationFunction {

    public SymmetricCosFunction() {
        super("neat.math.symmetric.cos(x, i)", "Symmetric Cosine", "Returns the trigonometric cosine of an angle in radians.", 
                -1.0d, 1.0d, 1,
                new String[] {"i"}, new String[] {"Input scaling"},
                new double[] {TwoPi}, new double[] {Double.NEGATIVE_INFINITY}, new double[] {Double.POSITIVE_INFINITY});
    }

    @Override
    protected double _compute(double activation, double[] parameters) {
        return Math.cos(activation * parameters[0]);
    }

}

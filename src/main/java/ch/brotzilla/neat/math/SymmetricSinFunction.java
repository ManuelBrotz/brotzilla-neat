package ch.brotzilla.neat.math;

public class SymmetricSinFunction extends ActivationFunction {

    public SymmetricSinFunction() {
        super("neat.math.symmetric.sin(x, i)", "Symmetric Sine", "Returns the trigonometric sine of an angle in radians.", 
                -1.0d, 1.0d, 1,
                new String[] {"i"}, new String[] {"Input scaling"},
                new double[] {TwoPi}, new double[] {Double.NEGATIVE_INFINITY}, new double[] {Double.POSITIVE_INFINITY});
    }

    @Override
    protected double _compute(double activation, double[] parameters) {
        return Math.sin(activation * parameters[0]);
    }

}

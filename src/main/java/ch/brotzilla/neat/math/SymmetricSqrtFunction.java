package ch.brotzilla.neat.math;

public class SymmetricSqrtFunction extends ActivationFunction {

    public SymmetricSqrtFunction() {
        super("neat.math.sqrt(x)", "Square Root", "Returns the positive square root of the argument.", 
                Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    @Override
    protected double _compute(double activation, double[] parameters) {
        if (activation < 0.0d) {
            return -Math.sqrt(-activation);
        }
        return Math.sqrt(activation);
    }

}

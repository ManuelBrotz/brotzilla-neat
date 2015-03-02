package ch.brotzilla.neat.math;

public class AbsFunction extends ActivationFunction {

    public AbsFunction() {
        super("neat.math.abs(x)", "Absolute Value", "Returns the absolute value of argument. If the argument is not negative, the argument is returned. If the argument is negative, the negation of the argument is returned.", 0.0d, Double.POSITIVE_INFINITY);
    }

    @Override
    protected double _compute(double activation, double[] parameters) {
        return Math.abs(activation);
    }

}

package ch.brotzilla.neat.math;

public class SqrtFunction extends ActivationFunction {

    public SqrtFunction() {
        super("neat.math.sqrt(x)", "Square Root", "Returns the positive square root of the argument.", 0.0d, Double.POSITIVE_INFINITY);
    }

    @Override
    protected double _compute(double activation, double[] parameters) {
        return Math.sqrt(activation);
    }

}

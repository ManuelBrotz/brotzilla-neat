package ch.brotzilla.neat.math;

public class SignumFunction extends ExtendedActivationFunction {

    public SignumFunction() {
        super("neat.math.extended.signum", "Signum", "Returns the signum function of the argument. If the argument is zero, 0.0 is returned. If the argument is greater than zero, 1.0 is returned. If the argument is less than zero, -1.0 is returned.");
    }

    @Override
    protected double _compute(double activation, double[] parameters) {
        return Math.signum(activation);
    }

}

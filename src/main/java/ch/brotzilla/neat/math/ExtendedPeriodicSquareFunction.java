package ch.brotzilla.neat.math;

public class ExtendedPeriodicSquareFunction extends ExtendedActivationFunction {

    public ExtendedPeriodicSquareFunction() {
        super("neat.math.extended.periodic.square", "Periodic Square", "Computes a periodic square function.");
    }

    @Override
    protected double _compute(double activation, double[] synapses) {
        return Math.signum(Math.cos(TwoPi * activation));
    }

}

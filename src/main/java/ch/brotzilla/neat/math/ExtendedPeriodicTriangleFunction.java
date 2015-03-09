package ch.brotzilla.neat.math;

public class ExtendedPeriodicTriangleFunction extends ExtendedActivationFunction {

    public ExtendedPeriodicTriangleFunction() {
        super("neat.math.extended.periodic.triangle", "Periodic Triangle", "Computes a periodic triangle function.");
    }

    @Override
    protected double _compute(double activation, double[] synapses) {
        return (2.0 * Math.asin(Math.cos(TwoPi * activation))) / Pi;
    }

}

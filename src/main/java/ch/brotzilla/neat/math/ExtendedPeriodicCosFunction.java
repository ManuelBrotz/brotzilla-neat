package ch.brotzilla.neat.math;

public class ExtendedPeriodicCosFunction extends ExtendedActivationFunction {

    public ExtendedPeriodicCosFunction() {
        super("neat.math.extended.periodic.cosine", "Periodic Cosine", "Returns the periodic cosine of the argument.");
    }

    @Override
    protected double _compute(double activation, double[] synapses) {
        return Math.cos(TwoPi * activation);
    }

}

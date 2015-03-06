package ch.brotzilla.neat.math;

public class ExtendedCosFunction extends ExtendedActivationFunction {

    public ExtendedCosFunction() {
        super("neat.math.extended.wave.cosine", "Cosine", "Returns the cosine of the argument.");
    }

    @Override
    protected double _compute(double activation, double[] synapses) {
        return Math.cos(TwoPi * activation);
    }

}

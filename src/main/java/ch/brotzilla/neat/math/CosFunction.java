package ch.brotzilla.neat.math;

public class CosFunction extends ExtendedActivationFunction {

    public CosFunction() {
        super("neat.math.extended.wave.cosine", "Cosine", "Returns the cosine of the argument.");
    }

    @Override
    protected double _compute(double activation, double[] synapses) {
        return Math.cos(TwoPi * activation);
    }

}

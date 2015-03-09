package ch.brotzilla.neat.math;

public class ExtendedDecliningCosFunction extends ExtendedDecliningActivationFunction {

    public ExtendedDecliningCosFunction() {
        super("neat.math.extended.declining.cosine", "Periodic Declining Cosine", "Returns the periodic, declining cosine of the argument.");
    }

    @Override
    protected double _compute(double activation, double[] synapses) {
        return Math.cos(TwoPi * activation);
    }

}

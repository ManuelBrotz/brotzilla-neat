package ch.brotzilla.neat.math;

public class ExtendedDecliningSawtoothFunction extends ExtendedDecliningActivationFunction {

    public ExtendedDecliningSawtoothFunction() {
        super("neat.math.extended.declining.sawtooth", "Periodic Declining Sawtooth", "Computes a periodic, declining sawtooth function.");
    }

    @Override
    protected double _compute(double activation, double[] synapses) {
        return (activation < 0.0 ? 2.0 : 0.0) + (2.0 * (activation % 1.0) - 1.0);
    }

}

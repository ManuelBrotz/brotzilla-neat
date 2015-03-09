package ch.brotzilla.neat.math;

public class ExtendedPeriodicSawtoothFunction extends ExtendedActivationFunction {

    public ExtendedPeriodicSawtoothFunction() {
        super("neat.math.extended.periodic.sawtooth", "Periodic Sawtooth", "Computes a periodic sawtooth function.");
    }

    @Override
    protected double _compute(double activation, double[] synapses) {
        return  (activation < 0.0 ? 2.0 : 0.0) + (2.0 * (activation % 1.0) - 1.0);
    }

}

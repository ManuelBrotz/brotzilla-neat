package ch.brotzilla.neat.math;

public class SawtoothWaveFunction extends ExtendedActivationFunction {

    public SawtoothWaveFunction() {
        super("neat.math.extended.wave.sawtooth", "Sawtooth Wave", "Computes a sawtooth wave function.");
    }

    @Override
    protected double _compute(double activation, double[] synapses) {
        return  (activation < 0.0 ? 2.0 : 0.0) + (2.0 * (activation % 1.0) - 1.0);
    }

}

package ch.brotzilla.neat.math;

public class ExtendedSquareWaveFunction extends ExtendedActivationFunction {

    public ExtendedSquareWaveFunction() {
        super("neat.math.extended.wave.square", "Square Wave", "Computes a square wave function.");
    }

    @Override
    protected double _compute(double activation, double[] synapses) {
        return Math.signum(Math.cos(TwoPi * activation));
    }

}

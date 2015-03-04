package ch.brotzilla.neat.math;

public class SquareWaveFunction extends ExtendedActivationFunction {

    public SquareWaveFunction() {
        super("neat.math.extended.wave.square", "Square Wave", "Computes a square wave function.");
    }

    @Override
    protected double _compute(double activation, double[] parameters) {
        return Math.signum(Math.cos(TwoPi * activation));
    }

}

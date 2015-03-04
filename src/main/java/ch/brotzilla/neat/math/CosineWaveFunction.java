package ch.brotzilla.neat.math;

public class CosineWaveFunction extends ExtendedActivationFunction {

    public CosineWaveFunction() {
        super("neat.math.extended.wave.cosine", "Cosine Wave", "Computes a cosine wave function.");
    }

    @Override
    protected double _compute(double activation, double[] parameters) {
        return Math.cos(TwoPi * activation);
    }

}

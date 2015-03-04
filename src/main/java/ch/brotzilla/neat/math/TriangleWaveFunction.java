package ch.brotzilla.neat.math;

public class TriangleWaveFunction extends ExtendedActivationFunction {

    public TriangleWaveFunction() {
        super("neat.math.extended.wave.triangle", "Triangle Wave", "Computes a triangle wave function.");
    }

    @Override
    protected double _compute(double activation, double[] parameters) {
        return (2.0 * Math.asin(Math.cos(TwoPi * activation))) / Pi;
    }

}

package ch.brotzilla.neat.math;

public class ExtendedTriangleWaveFunction extends ExtendedActivationFunction {

    public ExtendedTriangleWaveFunction() {
        super("neat.math.extended.wave.triangle", "Triangle Wave", "Computes a triangle wave function.");
    }

    @Override
    protected double _compute(double activation, double[] synapses) {
        return (2.0 * Math.asin(Math.cos(TwoPi * activation))) / Pi;
    }

}

package ch.brotzilla.neat.math;

public class ExtendedDecliningTriangleFunction extends ExtendedDecliningActivationFunction {

    public ExtendedDecliningTriangleFunction() {
        super("neat.math.extended.declining.triangle", "Periodic Declining Triangle", "Computes a periodic, declining triangle function.");
    }

    @Override
    protected double _compute(double activation, double[] synapses) {
        return (2.0 * Math.asin(Math.cos(TwoPi * activation))) / Pi;
    }

}

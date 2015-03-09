package ch.brotzilla.neat.math;

public class ExtendedDecliningSquareFunction extends ExtendedDecliningActivationFunction {

    public ExtendedDecliningSquareFunction() {
        super("neat.math.extended.declining.square", "Periodic Declining Square", "Computes a periodic, declining square function.");
    }

    @Override
    protected double _compute(double activation, double[] synapses) {
        return Math.signum(Math.cos(TwoPi * activation));
    }

}

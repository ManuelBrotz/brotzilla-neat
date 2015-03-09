package ch.brotzilla.neat.math;

import java.util.List;

public class ReciprocalFunction extends ActivationFunction {

    @Override
    protected void initializeDefaultSynapses(List<ActivationFunctionSynapse> synapses) {}

    public ReciprocalFunction() {
        super("neat.math.reciprocal", "Reciprocal", "Returns the reciprocal of the argument.");
    }

    @Override
    public double compute(double activation, double[] synapses) {
        return 1.0 / activation;
    }

}

package ch.brotzilla.neat.math;

import java.util.List;

public class ReciprocalFunction extends ActivationFunction {

    @Override
    protected void initializeDefaultSynapses(List<ActivationFunctionSynapse> synapses) {}

    public ReciprocalFunction() {
        super("neat.math.reciprocal", "Reciprocal", "Returns the reciprocal of the argument. Returns zero, if the argument is zero.");
    }

    @Override
    public double compute(double[] synapses) {
        final double activation = synapses[0];
        if (activation == 0.0) {
            return 0.0;
        }
        return 1.0 / activation;
    }

}

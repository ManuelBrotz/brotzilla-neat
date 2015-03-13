package ch.brotzilla.neat.math;

import java.util.List;

public class DivisionFunction extends ActivationFunction {

    @Override
    protected void initializeDefaultSynapses(List<ActivationFunctionSynapse> synapses) {
        final ActivationFunctionSynapse.Builder builder = new ActivationFunctionSynapse.Builder();
        synapses.add(builder.setName("divisor")
                .setDescription("Divides the input value of the activation function.")
                .setDefaultValue(1.0)
                .setViewerLowerBound(-10.0)
                .setViewerUpperBound(10.0)
                .build());
    }

    public DivisionFunction() {
        super("neat.math.division", "Division", "Divides the argument by the divisor. Returns zero, if the divisor is zero.");
    }

    @Override
    public double compute(double[] synapses) {
        final double divisor = synapses[1];
        if (divisor == 0.0) {
            return 0;
        }
        return synapses[0] / divisor;
    }

}

package ch.brotzilla.neat.math;

import java.util.List;

public abstract class ExtendedDecliningActivationFunction extends ExtendedActivationFunction {

    @Override
    protected void initializeDefaultSynapses(List<ActivationFunctionSynapse> synapses) {
        super.initializeDefaultSynapses(synapses);
        final ActivationFunctionSynapse.Builder builder = new ActivationFunctionSynapse.Builder();
        synapses.add(builder.setName("decline")
                .setDescription("Determines the steepness of the decline.")
                .setDefaultValue(1.0)
                .setViewerLowerBound(-5.0)
                .setViewerUpperBound(5.0)
                .build());
    }

    protected ExtendedDecliningActivationFunction(String id, String name, String description) {
        super(id, name, description);
    }

    @Override
    public double compute(double[] synapses) {
        final double result = super.compute(synapses);
        final double activation = synapses[0];
        final double d = synapses[6];
        final double decline = d < 0 ? -d : d;
        return result / (1.0 + activation * activation * decline);
    }
    
}

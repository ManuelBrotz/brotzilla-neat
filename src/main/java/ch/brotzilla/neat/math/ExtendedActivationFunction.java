package ch.brotzilla.neat.math;

import java.util.List;

import com.google.common.base.Preconditions;

// TODO All classes extending ExtendedActivationFunction should start with 'Extended'
public abstract class ExtendedActivationFunction extends ActivationFunction {

    private double rectify(double v, double r) {
        if (r > 0.0) {
            if (v < 0) return -v;
        } else if (r < 0.0) {
            if (v > 0) return -v;
        }
        return v;
    }
    
    @Override
    protected void initializeDefaultSynapses(List<ActivationFunctionSynapse> synapses) {
        final ActivationFunctionSynapse.Builder builder = new ActivationFunctionSynapse.Builder();
        synapses.add(builder.setName("input-scale")
                .setDescription("Scales the input value of the activation function.")
                .setDefaultValue(1.0)
                .setViewerLowerBound(-10.0)
                .setViewerUpperBound(10.0)
                .build());
        synapses.add(builder.setName("input-shift")
                .setDescription("Shifts the input value of the activation function.")
                .setDefaultValue(0.0)
                .setViewerLowerBound(-10.0)
                .setViewerUpperBound(10.0)
                .build());
        synapses.add(builder.setName("output-scale")
                .setDescription("Scales the output value of the activation function.")
                .setDefaultValue(1.0)
                .setViewerLowerBound(-10.0)
                .setViewerUpperBound(10.0)
                .build());
        synapses.add(builder.setName("output-shift")
                .setDescription("Shifts the output value of the activation function.")
                .setDefaultValue(0.0)
                .setViewerLowerBound(-10.0)
                .setViewerUpperBound(10.0)
                .build());
        synapses.add(builder.setName("rectify")
                .setDescription("If greater than 0, mapps all negative output values to positive ones. If less than 0, mapps all positive output values to negative ones. If equal to 0, the output values remain unchanged.")
                .setDefaultValue(0.0)
                .setViewerLowerBound(-1.0)
                .setViewerUpperBound(1.0)
                .build());
    }

    protected ExtendedActivationFunction(String id, String name, String description, ActivationFunctionSynapse... synapses) {
        super(id, name, description, synapses);
    }

    @Override
    public final double compute(double activation, double[] synapses) {
        Preconditions.checkNotNull(synapses, "The parameter 'synapses' must not be null");
        Preconditions.checkArgument(synapses.length >= getNumberOfSynapses(), "The length of the parameter 'synapses' has to be greater than or equal to " + getNumberOfSynapses());
        return rectify(_compute(activation * synapses[0] + synapses[1], synapses), synapses[4]) * synapses[2] + synapses[3];
    }
    
}

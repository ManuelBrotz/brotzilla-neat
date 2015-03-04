package ch.brotzilla.neat.math;

import java.util.List;

import com.google.common.base.Preconditions;

public abstract class ExtendedActivationFunction extends ActivationFunction {

    private double rectify(double v, double r) {
        if (r > 0.33) {
            if (v < 0) return -v;
        } else if (r < -0.33) {
            if (v > 0) return -v;
        }
        return v;
    }
    
    @Override
    protected void initializeDefaultParameters(List<ActivationFunctionParameter> parameters) {
        final ActivationFunctionParameter.Builder builder = new ActivationFunctionParameter.Builder();
        parameters.add(builder.setName("input-scale")
                .setDescription("Scales the input value of the activation function.")
                .setDefaultValue(1.0)
                .setViewerLowerBound(-10.0)
                .setViewerUpperBound(10.0)
                .build());
        parameters.add(builder.setName("input-shift")
                .setDescription("Shifts the input value of the activation function.")
                .setDefaultValue(0.0)
                .setViewerLowerBound(-10.0)
                .setViewerUpperBound(10.0)
                .build());
        parameters.add(builder.setName("output-scale")
                .setDescription("Scales the output value of the activation function.")
                .setDefaultValue(1.0)
                .setViewerLowerBound(-10.0)
                .setViewerUpperBound(10.0)
                .build());
        parameters.add(builder.setName("output-shift")
                .setDescription("Shifts the output value of the activation function.")
                .setDefaultValue(0.0)
                .setViewerLowerBound(-10.0)
                .setViewerUpperBound(10.0)
                .build());
        parameters.add(builder.setName("rectify")
                .setDescription("If greater than 0.33, mapps all negative output values to positive ones. If less than -0.33, mappes all positive output values to negative ones. If inbetween, the output values remain unchanged.")
                .setDefaultValue(0.0)
                .setViewerLowerBound(-1.0)
                .setViewerUpperBound(1.0)
                .build());
    }

    protected ExtendedActivationFunction(String id, String name, String description, ActivationFunctionParameter... parameters) {
        super(id, name, description, parameters);
    }

    @Override
    public final double compute(double activation, double[] parameters) {
        if (getNumberOfParameters() > 0) {
            Preconditions.checkNotNull(parameters, "The parameter 'parameters' must not be null");
            Preconditions.checkArgument(parameters.length == getNumberOfParameters(), "The length of the parameter 'parameters' has to be equal to " + getNumberOfParameters());
        } else {
            Preconditions.checkArgument(parameters == null, "The parameter 'parameters' has to be null");
        }
        return rectify(_compute(activation * parameters[0] + parameters[1], parameters), parameters[4]) * parameters[2] + parameters[3];
    }
    
}

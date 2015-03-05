package ch.brotzilla.neat.math;

import java.util.List;

import com.google.common.base.Preconditions;

public abstract class BasicActivationFunction extends ActivationFunction {

    @Override
    protected void initializeDefaultParameters(List<ActivationFunctionParameter> parameters) {}

    protected BasicActivationFunction(String id, String name, String description, ActivationFunctionParameter... parameters) {
        super(id, name, description, parameters);
    }

    @Override
    public final double compute(double activation, double[] parameters) {
        if (getNumberOfParameters() > 0) {
            Preconditions.checkNotNull(parameters, "The parameter 'parameters' must not be null");
            Preconditions.checkArgument(parameters.length >= getNumberOfParameters(), "The length of the parameter 'parameters' has to be greater than or equal to " + getNumberOfParameters());
        }
        return _compute(activation, parameters);
    }

}

package ch.brotzilla.neat.math;

import java.util.List;

import com.google.common.base.Preconditions;

public abstract class SimpleActivationFunction extends ActivationFunction {

    @Override
    protected final void initializeDefaultParameters(List<ActivationFunctionParameter> parameters) {}

    protected SimpleActivationFunction(String id, String name, String description) {
        super(id, name, description, (ActivationFunctionParameter[]) null);
    }

    @Override
    public final double compute(double activation, double[] parameters) {
        Preconditions.checkArgument(parameters == null, "The parameter 'parameters' has to be null");
        return _compute(activation, parameters);
    }

}

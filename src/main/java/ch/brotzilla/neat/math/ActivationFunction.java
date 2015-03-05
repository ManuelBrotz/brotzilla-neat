package ch.brotzilla.neat.math;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

// TODO Activation function parameters should be called 'synapses' throughout.
// TODO Get rid of parameter values in activation functions and handle them externally.
public abstract class ActivationFunction {

    public static final double Pi = Math.PI;
    public static final double TwoPi = 2 * Math.PI;

    private final String id, name, description;
    private final List<ActivationFunctionParameter> parameters;
    private final int numberOfParameters;
    private final double[] parameterValues;
    
    private List<ActivationFunctionParameter> initializeParameters(ActivationFunctionParameter[] parameters) {
        final List<ActivationFunctionParameter> result = Lists.newArrayList();
        initializeDefaultParameters(result);
        if (parameters != null) {
            for (final ActivationFunctionParameter p : parameters) {
                Preconditions.checkNotNull(p, "Activation function parameters must not be null");
                result.add(p);
            }
        }
        return Collections.unmodifiableList(result);
    }
    
    private double[] initializeDefaultValues() {
        final double[] result = new double[parameters.size()];
        int i = 0;
        for (final ActivationFunctionParameter p : parameters) {
            result[i++] = p.getDefaultValue();
        }
        return result;
    }

    protected abstract void initializeDefaultParameters(List<ActivationFunctionParameter> parameters);
    
    protected abstract double _compute(double activation, double[] parameters);

    protected ActivationFunction(String id, String name, String description, ActivationFunctionParameter... parameters) {
        Preconditions.checkNotNull(id, "The parameter 'id' must not be null");
        Preconditions.checkArgument(!id.trim().isEmpty(), "The parameter 'id' must not be empty");
        Preconditions.checkNotNull(name, "The parameter 'name' must not be null");
        Preconditions.checkArgument(!name.trim().isEmpty(), "The parameter 'name' must not be empty");
        Preconditions.checkNotNull(description, "The parameter 'description' must not be null");
        Preconditions.checkArgument(!description.trim().isEmpty(), "The parameter 'description' must not be empty");
        this.id = id;
        this.name = name;
        this.description = description;
        this.parameters = initializeParameters(parameters);
        this.numberOfParameters = this.parameters.size();
        this.parameterValues = initializeDefaultValues();
    }
    
    public String getID() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }

    public List<ActivationFunctionParameter> getParameters() {
        return parameters;
    }
    
    public int getNumberOfParameters() {
        return numberOfParameters;
    }
    
    public double[] copyDefaultValues() {
        if (numberOfParameters > 0) { 
            final double[] result = new double[numberOfParameters];
            int i = 0;
            for (final ActivationFunctionParameter p : parameters) {
                result[i++] = p.getDefaultValue();
            }
            return result;
        }
        return null;
    }

    public double getDefaultValue(int index) {
        return parameters.get(index).getDefaultValue();
    }
    
    public double[] copyParameterValues() {
        if (numberOfParameters > 0) {
            return Arrays.copyOf(parameterValues, parameterValues.length);
        }
        return null;
    }
    
    public double[] getParameterValues() {
        return parameterValues;
    }
    
    public double getParameterValue(int index) {
        return parameterValues[index];
    }
    
    public void resetParameterValues() {
        int i = 0;
        for (final ActivationFunctionParameter p : parameters) {
            parameterValues[i++] = p.getDefaultValue();
        }
    }
    
    public abstract double compute(double activation, double[] parameters);

    public final double compute(double activation) {
        return compute(activation, parameterValues);
    }
    
}

package ch.brotzilla.neat.math;

import com.google.common.base.Preconditions;

public abstract class ActivationFunction {

    public static final double TwoPi = 2 * Math.PI;

    private final ActivationFunctionInfo info;
    
    protected abstract double _compute(double activation, double[] parameters);
    
    protected ActivationFunction(ActivationFunctionInfo info) {
        Preconditions.checkNotNull(info, "The parameter 'info' must not be null");
        this.info = info;
    }
    
    protected ActivationFunction(String id, String name, String description, double resultMin, double resultMax, int numberOfParameters, String[] parameterNames, String[] parameterDescriptions, double[] defaults, double[] lowerBounds, double[] upperBounds) {
        this(new ActivationFunctionInfo(id, name, description, resultMin, resultMax, numberOfParameters, parameterNames, parameterDescriptions, defaults, lowerBounds, upperBounds));
    }
    
    protected ActivationFunction(String id, String name, String description, double resultMin, double resultMax) {
        this(new ActivationFunctionInfo(id, name, description, resultMin, resultMax, 0, null, null, null, null, null));
    }

    public ActivationFunctionInfo getInfo() {
        return info;
    }
    
    public int getNumberOfParameters() {
        return info.getNumberOfParameters();
    }
    
    public String getID() {
        return info.getID();
    }
    
    public double[] copyDefaults() {
        return info.copyDefaults();
    }
    
    public final double compute(double activation, double[] parameters) {
        if (getNumberOfParameters() > 0) {
            Preconditions.checkNotNull(parameters, "The parameter 'parameters' must not be null");
            Preconditions.checkArgument(parameters.length == getNumberOfParameters(), "The length of the parameter 'parameters' has to be equal to " + getNumberOfParameters());
        }
        return _compute(activation, parameters);
    }

    public final double compute(double activation) {
        return compute(activation, info.getDefaults());
    }
    
}

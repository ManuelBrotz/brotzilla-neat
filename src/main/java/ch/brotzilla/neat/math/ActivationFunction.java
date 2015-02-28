package ch.brotzilla.neat.math;

import java.util.Arrays;

import com.google.common.base.Preconditions;

public abstract class ActivationFunction {

    private final int numberOfParameters;
    private final String id, name, description;
    private final String[] names, descriptions;
    private final double[] defaults, lowerBounds, upperBounds;
    
    protected abstract double _compute(double activation, double[] parameters);
    
    public ActivationFunction(String id, String name, String description, int numberOfParameters, String[] parameterNames, String[] parameterDescriptions, double[] defaults, double[] lowerBounds, double[] upperBounds) {
        Preconditions.checkNotNull(id, "The parameter 'id' must not be null");
        Preconditions.checkArgument(!id.trim().isEmpty(), "The parameter 'id' must not be empty");
        Preconditions.checkNotNull(name, "The parameter 'name' must not be null");
        Preconditions.checkArgument(!name.trim().isEmpty(), "The parameter 'name' must not be empty");
        Preconditions.checkNotNull(description, "The parameter 'description' must not be null");
        Preconditions.checkArgument(!description.trim().isEmpty(), "The parameter 'description' must not be empty");
        Preconditions.checkArgument(numberOfParameters >= 0, "The parameter 'numberOfParameters' has to be greater than or equal to zero");
        Preconditions.checkNotNull(parameterNames, "The parameter 'parameterNames' must not be null");
        Preconditions.checkArgument(parameterNames.length == numberOfParameters, "The length of the parameter 'parameterNames' has to be equal to " + numberOfParameters);
        Preconditions.checkNotNull(parameterDescriptions, "The parameter 'parameterDescriptions' must not be null");
        Preconditions.checkArgument(parameterDescriptions.length == numberOfParameters, "The length of the parameter 'parameterDescriptions' has to be equal to " + numberOfParameters);
        this.id = id;
        this.name = name;
        this.description = description;
        this.numberOfParameters = numberOfParameters;
        this.names = Arrays.copyOf(parameterNames, parameterNames.length);
        this.descriptions = Arrays.copyOf(parameterDescriptions, parameterDescriptions.length);
        if (numberOfParameters > 0) {
            Preconditions.checkNotNull(defaults, "The parameter 'defaults' must not be null");
            Preconditions.checkArgument(defaults.length == numberOfParameters, "The length of the parameter 'defaults' has to be equal to " + numberOfParameters);
            Preconditions.checkNotNull(lowerBounds, "The parameter 'lowerBounds' must not be null");
            Preconditions.checkArgument(lowerBounds.length == numberOfParameters, "The length of the parameter 'lowerBounds' has to be equal to " + numberOfParameters);
            Preconditions.checkNotNull(upperBounds, "The parameter 'upperBounds' must not be null");
            Preconditions.checkArgument(upperBounds.length == numberOfParameters, "The length of the parameter 'upperBounds' has to be equal to " + numberOfParameters);
            this.defaults = Arrays.copyOf(defaults, defaults.length);
            this.lowerBounds = Arrays.copyOf(lowerBounds, lowerBounds.length);
            this.upperBounds = Arrays.copyOf(upperBounds, upperBounds.length);
        } else {
            Preconditions.checkArgument(defaults == null, "The parameter 'defaults' has to be null");
            Preconditions.checkArgument(lowerBounds == null, "The parameter 'lowerBounds' has to be null");
            Preconditions.checkArgument(upperBounds == null, "The parameter 'upperBounds' has to be null");
            this.defaults = null;
            this.lowerBounds = null;
            this.upperBounds = null;
        }
    }
    
    public final int getNumberOfParameters() {
        return numberOfParameters;
    }
    
    public final String getID() {
        return id;
    }
    
    public final String getName() {
        return name;
    }
    
    public final String getDescription() {
        return description;
    }
    
    public final String getName(int index) {
        return names[index];
    }
    
    public final String getDescription(int index) {
        return descriptions[index];
    }
    
    public final double[] copyDefaults() {
        if (defaults == null) {
            return null;
        }
        return Arrays.copyOf(defaults, defaults.length);
    }
    
    public final double getDefault(int index) {
        Preconditions.checkElementIndex(index, numberOfParameters, "The parameter 'index'");
        return defaults[index];
    }

    public final double[] copyLowerBounds() {
        if (lowerBounds == null) {
            return null;
        }
        return Arrays.copyOf(lowerBounds, lowerBounds.length);
    }
    
    public final double getLowerBound(int index) {
        Preconditions.checkElementIndex(index, numberOfParameters, "The parameter 'index'");
        return lowerBounds[index];
    }
    
    public final double[] copyUpperBounds() {
        if (upperBounds == null) {
            return null;
        }
        return Arrays.copyOf(upperBounds, upperBounds.length);
    }
    
    public final double getUpperBound(int index) {
        Preconditions.checkElementIndex(index, numberOfParameters, "The parameter 'index'");
        return upperBounds[index];
    }

    public final double compute(double activation, double[] parameters) {
        if (numberOfParameters > 0) {
            Preconditions.checkNotNull(parameters, "The parameter 'parameters' must not be null");
            Preconditions.checkArgument(parameters.length == numberOfParameters, "The length of the parameter 'parameters' has to be equal to " + numberOfParameters);
        }
        return _compute(activation, parameters);
    }

    public final double compute(double activation) {
        return compute(activation, defaults);
    }
    
}

package ch.brotzilla.neat.math;

import java.util.Arrays;

import com.google.common.base.Preconditions;

public class ActivationFunctionInfo {

    private final int numberOfParameters;
    private final String id, name, description;
    private final String[] names, descriptions;
    private final double resultMin, resultMax;
    private final double[] defaults, lowerBounds, upperBounds;

    double[] getDefaults() {
        return defaults;
    }
    
    public ActivationFunctionInfo(String id, String name, String description, double resultMin, double resultMax, int numberOfParameters, String[] parameterNames, String[] parameterDescriptions, double[] defaults, double[] lowerBounds, double[] upperBounds) {
        Preconditions.checkNotNull(id, "The parameter 'id' must not be null");
        Preconditions.checkArgument(!id.trim().isEmpty(), "The parameter 'id' must not be empty");
        Preconditions.checkNotNull(name, "The parameter 'name' must not be null");
        Preconditions.checkArgument(!name.trim().isEmpty(), "The parameter 'name' must not be empty");
        Preconditions.checkNotNull(description, "The parameter 'description' must not be null");
        Preconditions.checkArgument(!description.trim().isEmpty(), "The parameter 'description' must not be empty");
        Preconditions.checkArgument(numberOfParameters >= 0, "The parameter 'numberOfParameters' has to be greater than or equal to zero");
        this.id = id;
        this.name = name;
        this.description = description;
        this.resultMin = resultMin;
        this.resultMax = resultMax;
        this.numberOfParameters = numberOfParameters;
        if (numberOfParameters > 0) {
            Preconditions.checkNotNull(parameterNames, "The parameter 'parameterNames' must not be null");
            Preconditions.checkArgument(parameterNames.length == numberOfParameters, "The length of the parameter 'parameterNames' has to be equal to " + numberOfParameters);
            Preconditions.checkNotNull(parameterDescriptions, "The parameter 'parameterDescriptions' must not be null");
            Preconditions.checkArgument(parameterDescriptions.length == numberOfParameters, "The length of the parameter 'parameterDescriptions' has to be equal to " + numberOfParameters);
            Preconditions.checkNotNull(defaults, "The parameter 'defaults' must not be null");
            Preconditions.checkArgument(defaults.length == numberOfParameters, "The length of the parameter 'defaults' has to be equal to " + numberOfParameters);
            Preconditions.checkNotNull(lowerBounds, "The parameter 'lowerBounds' must not be null");
            Preconditions.checkArgument(lowerBounds.length == numberOfParameters, "The length of the parameter 'lowerBounds' has to be equal to " + numberOfParameters);
            Preconditions.checkNotNull(upperBounds, "The parameter 'upperBounds' must not be null");
            Preconditions.checkArgument(upperBounds.length == numberOfParameters, "The length of the parameter 'upperBounds' has to be equal to " + numberOfParameters);
            this.names = Arrays.copyOf(parameterNames, parameterNames.length);
            this.descriptions = Arrays.copyOf(parameterDescriptions, parameterDescriptions.length);
            this.defaults = Arrays.copyOf(defaults, defaults.length);
            this.lowerBounds = Arrays.copyOf(lowerBounds, lowerBounds.length);
            this.upperBounds = Arrays.copyOf(upperBounds, upperBounds.length);
        } else {
            Preconditions.checkArgument(parameterNames == null, "The parameter 'parameterNames' has to be null");
            Preconditions.checkArgument(parameterDescriptions == null, "The parameter 'parameterDescriptions' has to be null");
            Preconditions.checkArgument(defaults == null, "The parameter 'defaults' has to be null");
            Preconditions.checkArgument(lowerBounds == null, "The parameter 'lowerBounds' has to be null");
            Preconditions.checkArgument(upperBounds == null, "The parameter 'upperBounds' has to be null");
            this.names = null;
            this.descriptions = null;
            this.defaults = null;
            this.lowerBounds = null;
            this.upperBounds = null;
        }
    }

    public ActivationFunctionInfo(String id, String name, String description, double resultMin, double resultMax) {
        this(id, name, description, resultMin, resultMax, 0, null, null, null, null, null);
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

    public double getResultMin() {
        return resultMin;
    }
    
    public double getResultMax() {
        return resultMax;
    }
    
    public int getNumberOfParameters() {
        return numberOfParameters;
    }
    
    public String[] copyNames() {
        if (names == null) {
            return null;
        }
        return Arrays.copyOf(names, names.length);
    }
    
    public String getName(int index) {
        Preconditions.checkElementIndex(index, numberOfParameters, "The parameter 'index'");
        return names[index];
    }
    
    public String[] copyDescriptions() {
        if (descriptions == null) {
            return null;
        }
        return Arrays.copyOf(descriptions, descriptions.length);
    }
    
    public String getDescription(int index) {
        Preconditions.checkElementIndex(index, numberOfParameters, "The parameter 'index'");
        return descriptions[index];
    }
    
    public double[] copyDefaults() {
        if (defaults == null) {
            return null;
        }
        return Arrays.copyOf(defaults, defaults.length);
    }
    
    public double getDefault(int index) {
        Preconditions.checkElementIndex(index, numberOfParameters, "The parameter 'index'");
        return defaults[index];
    }

    public double[] copyLowerBounds() {
        if (lowerBounds == null) {
            return null;
        }
        return Arrays.copyOf(lowerBounds, lowerBounds.length);
    }
    
    public double getLowerBound(int index) {
        Preconditions.checkElementIndex(index, numberOfParameters, "The parameter 'index'");
        return lowerBounds[index];
    }
    
    public double[] copyUpperBounds() {
        if (upperBounds == null) {
            return null;
        }
        return Arrays.copyOf(upperBounds, upperBounds.length);
    }
    
    public double getUpperBound(int index) {
        Preconditions.checkElementIndex(index, numberOfParameters, "The parameter 'index'");
        return upperBounds[index];
    }

}

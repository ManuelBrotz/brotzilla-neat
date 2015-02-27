package ch.brotzilla.neat.genome;

import java.util.Arrays;

import ch.brotzilla.neat.math.ActivationFunction;

import com.google.common.base.Preconditions;

public class Node {

    private final NodeType type;
    private final int innovationNumber;
    private ActivationFunction activationFunction;
    private double[] defaultParameters;

    public Node(NodeType type, int innovationNumber, ActivationFunction activationFunction, double[] defaultParameters) {
        Preconditions.checkNotNull(type, "The parameter 'type' must not be null");
        Preconditions.checkArgument(innovationNumber > 0, "The parameter 'innovationNumber' has to be greater than zero");
        this.type = type;
        this.innovationNumber = innovationNumber;
        if (type.isInputNode()) {
            Preconditions.checkArgument(activationFunction == null, "The parameter 'activationFunction' has to be null for input nodes");
            Preconditions.checkArgument(defaultParameters == null, "The parameter 'defaultParameters' has to be null for input nodes");
            this.activationFunction = null;
            this.defaultParameters = null;
        } else {
            Preconditions.checkNotNull(activationFunction, "The parameter 'activationFunction' must not be null");
            this.activationFunction = activationFunction;
            if (defaultParameters == null) {
                this.defaultParameters = activationFunction.copyDefaults();
            } else {
                Preconditions.checkArgument(defaultParameters.length == activationFunction.getNumberOfParameters(), "The length of the parameter 'defaultParameters' has to be equal to " + activationFunction.getNumberOfParameters());
                this.defaultParameters = Arrays.copyOf(defaultParameters, defaultParameters.length);
            }
        }
    }
    
    public Node(NodeType type, int innovationNumber, ActivationFunction activationFunction) {
        this(type, innovationNumber, activationFunction, null);
    }
    
    public NodeType getType() {
        return type;
    }
    
    public int getInnovationNumber() {
        return innovationNumber;
    }

    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }
    
    public void setActivationFunction(ActivationFunction value) {
        if (type.isInputNode()) {
            throw new UnsupportedOperationException("Input nodes cannot have activation functions");
        } 
        Preconditions.checkNotNull(value, "The parameter 'value' must not be null");
        activationFunction = value;
        defaultParameters = value.copyDefaults();
    }
    
    public double[] getDefaultParameters() {
        return defaultParameters;
    }
    
    @Override
    public int hashCode() {
        return innovationNumber;
    }

}

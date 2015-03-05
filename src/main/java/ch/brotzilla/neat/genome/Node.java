package ch.brotzilla.neat.genome;

import java.util.Arrays;

import ch.brotzilla.neat.math.ActivationFunction;

import com.google.common.base.Preconditions;

// TODO Parameters should be called 'synapses' throughout.
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
                this.defaultParameters = activationFunction.copyParameterValues();
            } else {
                Preconditions.checkArgument(defaultParameters.length == activationFunction.getNumberOfParameters(), "The length of the parameter 'defaultParameters' has to be equal to " + activationFunction.getNumberOfParameters());
                this.defaultParameters = Arrays.copyOf(defaultParameters, defaultParameters.length);
            }
        }
    }
    
    public Node(NodeType type, int innovationNumber, ActivationFunction activationFunction) {
        this(type, innovationNumber, activationFunction, null);
    }
    
    public Node(NodeType type, int innovationNumber) {
        this(type, innovationNumber, null, null);
    }
    
    public Node(Node source) {
        Preconditions.checkNotNull(source, "The parameter 'source' must not be null");
        type = source.type;
        innovationNumber = source.innovationNumber;
        activationFunction = source.activationFunction;
        defaultParameters = (source.defaultParameters == null ? null : Arrays.copyOf(source.defaultParameters, source.defaultParameters.length));
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
        defaultParameters = value.copyParameterValues();
    }
    
    public double[] getDefaultParameters() {
        return defaultParameters;
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Node) {
            final Node node = (Node) other;
            return type == node.type
                    && innovationNumber == node.innovationNumber
                    && activationFunction == node.activationFunction
                    && Arrays.equals(defaultParameters, node.defaultParameters);
        }
        return false;
    }
    
    @Override
    public Node clone() {
        return new Node(this);
    }
}

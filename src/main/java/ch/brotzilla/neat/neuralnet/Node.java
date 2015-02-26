package ch.brotzilla.neat.neuralnet;

import ch.brotzilla.neat.math.ActivationFunction;

import com.google.common.base.Preconditions;

public abstract class Node {

    private final NodeType type;
    private final int innovationNumber;
    private ActivationFunction activationFunction;

    protected Node(NodeType type, int innovationNumber, ActivationFunction activationFunction) {
        Preconditions.checkNotNull(type, "The parameter 'type' must not be null");
        Preconditions.checkArgument(innovationNumber > 0, "The parameter 'innovationNumber' has to be greater than zero");
        Preconditions.checkNotNull(activationFunction, "The parameter 'activationFunction' must not be null");
        this.type = type;
        this.innovationNumber = innovationNumber;
        this.activationFunction = activationFunction;
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
        Preconditions.checkNotNull(value, "The parameter 'value' must not be null");
        activationFunction = value;
    }
    
}

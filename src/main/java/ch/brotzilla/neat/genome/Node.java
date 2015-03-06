package ch.brotzilla.neat.genome;

import java.util.Arrays;

import ch.brotzilla.neat.math.ActivationFunction;

import com.google.common.base.Preconditions;

public class Node {

    private final NodeType type;
    private final int innovationNumber;
    private ActivationFunction activationFunction;
    private double[] synapseDefaults;

    public Node(NodeType type, int innovationNumber, ActivationFunction activationFunction, double[] synapseDefaults) {
        Preconditions.checkNotNull(type, "The parameter 'type' must not be null");
        Preconditions.checkArgument(innovationNumber > 0, "The parameter 'innovationNumber' has to be greater than zero");
        this.type = type;
        this.innovationNumber = innovationNumber;
        if (type.isInputNode()) {
            Preconditions.checkArgument(activationFunction == null, "The parameter 'activationFunction' has to be null for input nodes");
            Preconditions.checkArgument(synapseDefaults == null, "The parameter 'synapseDefaults' has to be null for input nodes");
            this.activationFunction = null;
            this.synapseDefaults = null;
        } else {
            Preconditions.checkNotNull(activationFunction, "The parameter 'activationFunction' must not be null");
            this.activationFunction = activationFunction;
            if (activationFunction.getNumberOfSynapses() > 0) {
                if (synapseDefaults == null) {
                    this.synapseDefaults = activationFunction.copySynapseDefaults();
                } else {
                    Preconditions.checkArgument(synapseDefaults.length == activationFunction.getNumberOfSynapses(), "The length of the parameter 'synapseDefaults' has to be equal to " + activationFunction.getNumberOfSynapses());
                    this.synapseDefaults = Arrays.copyOf(synapseDefaults, synapseDefaults.length);
                }
            } else {
                Preconditions.checkArgument(synapseDefaults == null, "The parameter 'synapseDefaults' has to be null");
                this.synapseDefaults = null;
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
        synapseDefaults = (source.synapseDefaults == null ? null : Arrays.copyOf(source.synapseDefaults, source.synapseDefaults.length));
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
    
    public void setActivationFunction(ActivationFunction activationFunction, double[] synapseDefaults) {
        if (type.isInputNode()) {
            throw new UnsupportedOperationException("Input nodes cannot have activation functions");
        } 
        Preconditions.checkNotNull(activationFunction, "The parameter 'activationFunction' must not be null");
        this.activationFunction = activationFunction;
        if (activationFunction.getNumberOfSynapses() > 0) {
            if (synapseDefaults == null) {
                this.synapseDefaults = activationFunction.copySynapseDefaults();
            } else {
                Preconditions.checkArgument(synapseDefaults.length == activationFunction.getNumberOfSynapses(), "The length of the parameter 'synapseDefaults' has to be equal to " + activationFunction.getNumberOfSynapses());
                this.synapseDefaults = Arrays.copyOf(synapseDefaults, synapseDefaults.length);
            }
        } else {
            Preconditions.checkArgument(synapseDefaults == null, "The parameter 'synapseDefaults' has to be null");
            this.synapseDefaults = null;
        }
    }
    
    public double[] getSynapseDefaults() {
        return synapseDefaults;
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
                    && Arrays.equals(synapseDefaults, node.synapseDefaults);
        }
        return false;
    }
    
    @Override
    public Node clone() {
        return new Node(this);
    }
}

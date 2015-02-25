package ch.brotzilla.neat.neuralnet;

import com.google.common.base.Preconditions;

public abstract class Neuron {

    private final NeuralNet owner;
    private int innovationNumber;

    protected Neuron(NeuralNet owner) {
        Preconditions.checkNotNull(owner, "The parameter 'owner' must not be null");
        this.owner = owner;
    }
    
    public NeuralNet getOwner() {
        return owner;
    }
    
    public int getInnovationNumber() {
        return innovationNumber;
    }
    
    public void setInnovationNumber(int value) {
        Preconditions.checkArgument(value >= 0, "The parameter 'value' has to be greater than or equal to zero");
        innovationNumber = value;
    }
    
    public abstract double getActivation();

}

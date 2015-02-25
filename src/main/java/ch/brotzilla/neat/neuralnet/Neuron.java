package ch.brotzilla.neat.neuralnet;

import com.google.common.base.Preconditions;

public abstract class Neuron {

    private final NeuralNet owner;

    protected Neuron(NeuralNet owner) {
        Preconditions.checkNotNull(owner, "The parameter 'owner' must not be null");
        this.owner = owner;
    }
    
    public NeuralNet getOwner() {
        return owner;
    }
    
    public abstract double getActivation();

}

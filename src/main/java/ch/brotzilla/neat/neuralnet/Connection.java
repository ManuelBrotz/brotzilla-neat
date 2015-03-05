package ch.brotzilla.neat.neuralnet;

import com.google.common.base.Preconditions;

public abstract class Connection {
    
    protected final NeuralNet owner;
    protected final int synapse;
    protected final double weight;
    
    public Connection(NeuralNet owner, int synapse, double weight) {
        Preconditions.checkNotNull(owner, "The parameter 'owner' must not be null");
        Preconditions.checkArgument(synapse >= -1, "The parameter 'synapse' has to be greater than or equal to -1");
        Preconditions.checkArgument(!Double.isInfinite(weight) && !Double.isNaN(weight), "The parameter 'weight' has to be a valid double value (neither ininity nor nan are permitted)");
        this.owner = owner;
        this.synapse = synapse;
        this.weight = weight;
    }

    public NeuralNet getOwner() {
        return owner;
    }
    
    public int getSynapse() {
        return synapse;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public abstract double getValue();
    
}

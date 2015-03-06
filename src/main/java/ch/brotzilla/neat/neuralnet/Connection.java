package ch.brotzilla.neat.neuralnet;

import com.google.common.base.Preconditions;

public abstract class Connection {
    
    protected final NeuralNet owner;
    protected final double weight;
    
    public Connection(NeuralNet owner, double weight) {
        Preconditions.checkNotNull(owner, "The parameter 'owner' must not be null");
        Preconditions.checkArgument(!Double.isInfinite(weight) && !Double.isNaN(weight), "The parameter 'weight' has to be a valid double value (neither ininity nor nan are permitted)");
        this.owner = owner;
        this.weight = weight;
    }

    public NeuralNet getOwner() {
        return owner;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public abstract double getValue();
    
}

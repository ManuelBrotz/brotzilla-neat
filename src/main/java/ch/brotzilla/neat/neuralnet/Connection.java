package ch.brotzilla.neat.neuralnet;

import com.google.common.base.Preconditions;

public abstract class Connection {
    
    protected final int synapse;
    protected final double weight;
    
    public Connection(int synapse, double weight) {
        Preconditions.checkArgument(!Double.isInfinite(weight) && !Double.isNaN(weight), "The parameter 'weight' has to be a valid double value (neither ininity nor nan are permitted)");
        Preconditions.checkArgument(synapse >= 0, "The parameter 'synapse' has to be greater than or equal to zero");
        this.synapse = synapse;
        this.weight = weight;
    }

    public int getSynapse() {
        return synapse;
    }

    public double getWeight() {
        return weight;
    }
    
    public abstract double getValue(NeuralNet nn);
    
}

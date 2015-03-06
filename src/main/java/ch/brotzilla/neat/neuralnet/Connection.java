package ch.brotzilla.neat.neuralnet;

import com.google.common.base.Preconditions;

public abstract class Connection {
    
    protected final double weight;
    
    public Connection(double weight) {
        Preconditions.checkArgument(!Double.isInfinite(weight) && !Double.isNaN(weight), "The parameter 'weight' has to be a valid double value (neither ininity nor nan are permitted)");
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }
    
    public abstract double getValue(NeuralNet nn);
    
}

package ch.brotzilla.neat.neuralnet;

import com.google.common.base.Preconditions;

public abstract class NeuronConnection extends Connection {

    protected final int neuronIndex;
    
    public NeuronConnection(int neuronIndex, double weight) {
        super(weight);
        Preconditions.checkArgument(neuronIndex >= 0, "The parameter 'neuronIndex' has to be greater than or equal to zero");
        this.neuronIndex = neuronIndex;
    }
    
    public int getNeuronIndex() {
        return neuronIndex;
    }

}

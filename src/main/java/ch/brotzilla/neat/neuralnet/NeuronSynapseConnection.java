package ch.brotzilla.neat.neuralnet;

import com.google.common.base.Preconditions;

public abstract class NeuronSynapseConnection extends SynapseConnection {

    protected final int neuronIndex;
    
    public NeuronSynapseConnection(int neuronIndex, int synapse, double weight) {
        super(synapse, weight);
        Preconditions.checkArgument(neuronIndex >= 0, "The parameter 'neuronIndex' has to be greater than or equal to zero");
        this.neuronIndex = neuronIndex;
    }
    
    public int getNeuronIndex() {
        return neuronIndex;
    }

}

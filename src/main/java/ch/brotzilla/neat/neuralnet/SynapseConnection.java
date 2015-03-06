package ch.brotzilla.neat.neuralnet;

import com.google.common.base.Preconditions;

public abstract class SynapseConnection extends Connection {

    protected final int synapse;

    public SynapseConnection(int synapse, double weight) {
        super(weight);
        Preconditions.checkArgument(synapse >= 0, "The parameter 'synapse' has to be greater than or equal to zero");
        this.synapse = synapse;
    }

    public int getSynapse() {
        return synapse;
    }
    
}

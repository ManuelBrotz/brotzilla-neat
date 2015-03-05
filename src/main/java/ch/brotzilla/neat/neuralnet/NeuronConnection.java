package ch.brotzilla.neat.neuralnet;

public abstract class NeuronConnection extends Connection {

    protected final int neuronIndex;
    
    public NeuronConnection(NeuralNet owner, int neuronIndex, int synapse, double weight) {
        super(owner, synapse, weight);
        this.neuronIndex = neuronIndex;
    }
    
    public int getNeuronIndex() {
        return neuronIndex;
    }

}

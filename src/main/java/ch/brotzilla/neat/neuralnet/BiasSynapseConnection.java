package ch.brotzilla.neat.neuralnet;

public class BiasSynapseConnection extends SynapseConnection {

    public BiasSynapseConnection(NeuralNet owner, int synapse, double weight) {
        super(owner, synapse, weight);
    }

    @Override
    public double getValue() {
        return weight;
    }

}

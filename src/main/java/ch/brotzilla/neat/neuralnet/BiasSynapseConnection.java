package ch.brotzilla.neat.neuralnet;

public class BiasSynapseConnection extends SynapseConnection {

    public BiasSynapseConnection(int synapse, double weight) {
        super(synapse, weight);
    }

    @Override
    public double getValue(NeuralNet nn) {
        return weight;
    }

}

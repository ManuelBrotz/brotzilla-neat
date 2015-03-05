package ch.brotzilla.neat.neuralnet;

public class BiasConnection extends Connection {

    public BiasConnection(NeuralNet owner, int synapse, double weight) {
        super(owner, synapse, weight);
    }

    @Override
    public double getValue() {
        return weight;
    }

}

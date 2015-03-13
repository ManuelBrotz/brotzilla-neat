package ch.brotzilla.neat.neuralnet;

public class BiasConnection extends Connection {

    public BiasConnection(int synapse, double weight) {
        super(synapse, weight);
    }

    @Override
    public double getValue(NeuralNet nn) {
        return weight;
    }

}

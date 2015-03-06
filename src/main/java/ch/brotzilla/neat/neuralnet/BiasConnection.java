package ch.brotzilla.neat.neuralnet;

public class BiasConnection extends Connection {

    public BiasConnection(NeuralNet owner, double weight) {
        super(owner, weight);
    }

    @Override
    public double getValue() {
        return weight;
    }

}

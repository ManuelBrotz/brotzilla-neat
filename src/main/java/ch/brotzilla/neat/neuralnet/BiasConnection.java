package ch.brotzilla.neat.neuralnet;

public class BiasConnection extends Connection {

    public BiasConnection(double weight) {
        super(weight);
    }

    @Override
    public double getValue(NeuralNet nn) {
        return weight;
    }

}

package ch.brotzilla.neat.neuralnet;

public class HiddenNeuronConnection extends NeuronConnection {

    public HiddenNeuronConnection(int neuronIndex, double weight) {
        super(neuronIndex, weight);
    }

    @Override
    public double getValue(NeuralNet nn) {
        return nn.getHiddenNeuronActivation(neuronIndex) * weight;
    }

}

package ch.brotzilla.neat.neuralnet;

public class InputNeuronConnection extends NeuronConnection {

    public InputNeuronConnection(int neuronIndex, double weight) {
        super(neuronIndex, weight);
    }

    @Override
    public double getValue(NeuralNet nn) {
        return nn.getInputNeuronActivation(neuronIndex) * weight;
    }

}

package ch.brotzilla.neat.neuralnet;

public class OutputNeuronConnection extends NeuronConnection {

    public OutputNeuronConnection(int neuronIndex, double weight) {
        super(neuronIndex, weight);
    }

    @Override
    public double getValue(NeuralNet nn) {
        return nn.getOutputNeuronActivation(neuronIndex) * weight;
    }

}

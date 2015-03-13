package ch.brotzilla.neat.neuralnet;

public class InputNeuronConnection extends NeuronConnection {

    public InputNeuronConnection(int neuronIndex, int synapse, double weight) {
        super(neuronIndex, synapse, weight);
    }

    @Override
    public double getValue(NeuralNet nn) {
        return nn.getInputNeuronActivation(neuronIndex) * weight;
    }

}

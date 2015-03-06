package ch.brotzilla.neat.neuralnet;

public class InputNeuronSynapseConnection extends NeuronSynapseConnection {

    public InputNeuronSynapseConnection(int neuronIndex, int synapse, double weight) {
        super(neuronIndex, synapse, weight);
    }

    @Override
    public double getValue(NeuralNet nn) {
        return nn.getInputNeuronActivation(neuronIndex) * weight;
    }

}

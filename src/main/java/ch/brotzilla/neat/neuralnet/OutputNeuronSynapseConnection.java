package ch.brotzilla.neat.neuralnet;

public class OutputNeuronSynapseConnection extends NeuronSynapseConnection {

    public OutputNeuronSynapseConnection(int neuronIndex, int synapse, double weight) {
        super(neuronIndex, synapse, weight);
    }

    @Override
    public double getValue(NeuralNet nn) {
        return nn.getOutputNeuronActivation(neuronIndex) * weight;
    }

}

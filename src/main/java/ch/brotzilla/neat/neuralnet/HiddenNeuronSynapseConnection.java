package ch.brotzilla.neat.neuralnet;

public class HiddenNeuronSynapseConnection extends NeuronSynapseConnection {

    public HiddenNeuronSynapseConnection(int neuronIndex, int synapse, double weight) {
        super(neuronIndex, synapse, weight);
    }

    @Override
    public double getValue(NeuralNet nn) {
        return nn.getHiddenNeuronActivation(neuronIndex) * weight;
    }

}

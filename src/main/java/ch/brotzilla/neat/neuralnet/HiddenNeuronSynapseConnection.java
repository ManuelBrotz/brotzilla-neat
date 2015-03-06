package ch.brotzilla.neat.neuralnet;

import com.google.common.base.Preconditions;

public class HiddenNeuronSynapseConnection extends NeuronSynapseConnection {

    public HiddenNeuronSynapseConnection(NeuralNet owner, int neuronIndex, int synapse, double weight) {
        super(owner, neuronIndex, synapse, weight);
        Preconditions.checkElementIndex(neuronIndex, owner.getNumberOfHiddenNeurons(), "The parameter 'neuronIndex'");
    }

    @Override
    public double getValue() {
        return owner.getHiddenNeuronActivation(neuronIndex) * weight;
    }

}

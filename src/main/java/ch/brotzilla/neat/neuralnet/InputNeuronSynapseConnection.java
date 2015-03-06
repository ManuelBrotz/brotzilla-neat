package ch.brotzilla.neat.neuralnet;

import com.google.common.base.Preconditions;

public class InputNeuronSynapseConnection extends NeuronSynapseConnection {

    public InputNeuronSynapseConnection(NeuralNet owner, int neuronIndex, int synapse, double weight) {
        super(owner, neuronIndex, synapse, weight);
        Preconditions.checkElementIndex(neuronIndex, owner.getNumberOfInputNeurons(), "The parameter 'neuronIndex'");
    }

    @Override
    public double getValue() {
        return owner.getInputNeuronActivation(neuronIndex) * weight;
    }

}
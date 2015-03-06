package ch.brotzilla.neat.neuralnet;

import com.google.common.base.Preconditions;

public class OutputNeuronConnection extends NeuronConnection {

    public OutputNeuronConnection(NeuralNet owner, int neuronIndex, double weight) {
        super(owner, neuronIndex, weight);
        Preconditions.checkElementIndex(neuronIndex, owner.getNumberOfOutputNeurons(), "The parameter 'neuronIndex'");
    }

    @Override
    public double getValue() {
        return owner.getOutputNeuronActivation(neuronIndex) * weight;
    }

}

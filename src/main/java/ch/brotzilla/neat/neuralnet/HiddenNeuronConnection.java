package ch.brotzilla.neat.neuralnet;

import com.google.common.base.Preconditions;

public class HiddenNeuronConnection extends NeuronConnection {

    public HiddenNeuronConnection(NeuralNet owner, int neuronIndex, double weight) {
        super(owner, neuronIndex, weight);
        Preconditions.checkElementIndex(neuronIndex, owner.getNumberOfHiddenNeurons(), "The parameter 'neuronIndex'");
    }

    @Override
    public double getValue() {
        return owner.getHiddenNeuronActivation(neuronIndex) * weight;
    }

}

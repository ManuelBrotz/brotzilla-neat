package ch.brotzilla.neat.neuralnet;

import com.google.common.base.Preconditions;

public class InputNeuronConnection extends NeuronConnection {

    public InputNeuronConnection(NeuralNet owner, int neuronIndex, double weight) {
        super(owner, neuronIndex, weight);
        Preconditions.checkElementIndex(neuronIndex, owner.getNumberOfInputNeurons(), "The parameter 'neuronIndex'");
    }

    @Override
    public double getValue() {
        return owner.getInputNeuronActivation(neuronIndex) * weight;
    }

}

package ch.brotzilla.neat.neuralnet;

import com.google.common.base.Preconditions;

import ch.brotzilla.neat.math.ActivationFunction;

public class SimpleHiddenNeuron extends SimpleNeuron {

    public SimpleHiddenNeuron(NeuralNet owner, int neuronIndex, ActivationFunction activationFunction, Connection[] connections) {
        super(owner, neuronIndex, activationFunction, connections);
        Preconditions.checkElementIndex(neuronIndex, owner.getNumberOfHiddenNeurons(), "The parameter 'neuronIndex'");
    }

    @Override
    public void setActivation(double activation) {
        owner.setHiddenNeuronActivation(neuronIndex, activation);
    }

}

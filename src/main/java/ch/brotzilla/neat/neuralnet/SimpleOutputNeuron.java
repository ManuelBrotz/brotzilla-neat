package ch.brotzilla.neat.neuralnet;

import com.google.common.base.Preconditions;

import ch.brotzilla.neat.math.ActivationFunction;

public class SimpleOutputNeuron extends SimpleNeuron {

    @Override
    protected void setActivation(double activation) {
        owner.setOutputNeuronActivation(neuronIndex, activation);
    }

    public SimpleOutputNeuron(NeuralNet owner, int neuronIndex, ActivationFunction activationFunction, Connection[] connections) {
        super(owner, neuronIndex, activationFunction, connections);
        Preconditions.checkElementIndex(neuronIndex, owner.getNumberOfOutputNeurons(), "The parameter 'neuronIndex'");
    }

}

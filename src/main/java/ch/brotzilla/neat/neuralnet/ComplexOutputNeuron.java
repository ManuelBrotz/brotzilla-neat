package ch.brotzilla.neat.neuralnet;

import com.google.common.base.Preconditions;

import ch.brotzilla.neat.math.ActivationFunction;

public class ComplexOutputNeuron extends SimpleNeuron {

    public ComplexOutputNeuron(NeuralNet owner, int neuronIndex, ActivationFunction activationFunction, Connection[] connections) {
        super(owner, neuronIndex, activationFunction, connections);
        Preconditions.checkElementIndex(neuronIndex, owner.getNumberOfOutputNeurons(), "The parameter 'neuronIndex'");
    }

    @Override
    public void setActivation(double activation) {
        owner.setOutputNeuronActivation(neuronIndex, activation);
    }

}

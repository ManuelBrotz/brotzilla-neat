package ch.brotzilla.neat.neuralnet;

import com.google.common.base.Preconditions;

import ch.brotzilla.neat.math.ActivationFunction;

public class ComplexHiddenNeuron extends ComplexNeuron {

    @Override
    protected void setActivation(double activation) {
        owner.setHiddenNeuronActivation(neuronIndex, activation);
    }

    public ComplexHiddenNeuron(NeuralNet owner, int neuronIndex, ActivationFunction activationFunction, double[] synapseDefaults, Connection[] connections) {
        super(owner, neuronIndex, activationFunction, synapseDefaults, connections);
        Preconditions.checkElementIndex(neuronIndex, owner.getNumberOfHiddenNeurons(), "The parameter 'neuronIndex'");
    }

}

package ch.brotzilla.neat.neuralnet;

import com.google.common.base.Preconditions;

import ch.brotzilla.neat.math.ActivationFunction;

public class ComplexOutputNeuron extends ComplexNeuron {

    @Override
    protected void setActivation(double activation) {
        owner.setOutputNeuronActivation(neuronIndex, activation);
    }

    public ComplexOutputNeuron(NeuralNet owner, int neuronIndex, ActivationFunction activationFunction, double[] synapseDefaults, Connection[] connections, SynapseConnection[] synapseConnections) {
        super(owner, neuronIndex, activationFunction, synapseDefaults, connections, synapseConnections);
        Preconditions.checkElementIndex(neuronIndex, owner.getNumberOfOutputNeurons(), "The parameter 'neuronIndex'");
    }

}

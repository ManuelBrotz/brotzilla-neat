package ch.brotzilla.neat.neuralnet;

import ch.brotzilla.neat.math.ActivationFunction;

public class ComplexHiddenNeuron extends ComplexNeuron {

    @Override
    protected void setActivation(NeuralNet nn, double activation) {
        nn.setHiddenNeuronActivation(neuronIndex, activation);
    }

    public ComplexHiddenNeuron(int neuronIndex, ActivationFunction activationFunction, double[] synapseDefaults, Connection[] connections, Connection[] synapseConnections) {
        super(neuronIndex, activationFunction, synapseDefaults, connections, synapseConnections);
    }

}

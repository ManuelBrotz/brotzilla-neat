package ch.brotzilla.neat.neuralnet;

import ch.brotzilla.neat.math.ActivationFunction;

public class ComplexOutputNeuron extends ComplexNeuron {

    @Override
    protected void setActivation(NeuralNet nn, double activation) {
        nn.setOutputNeuronActivation(neuronIndex, activation);
    }

    public ComplexOutputNeuron(int neuronIndex, ActivationFunction activationFunction, double[] synapseDefaults, Connection[] connections, Connection[] synapseConnections) {
        super(neuronIndex, activationFunction, synapseDefaults, connections, synapseConnections);
    }

}

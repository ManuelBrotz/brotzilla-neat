package ch.brotzilla.neat.neuralnet;

import ch.brotzilla.neat.math.ActivationFunction;

public class HiddenNeuron extends Neuron {

    @Override
    protected void setActivation(NeuralNet nn, double activation) {
        nn.setHiddenNeuronActivation(neuronIndex, activation);
    }

    public HiddenNeuron(int neuronIndex, ActivationFunction activationFunction, double[] synapseDefaults, Connection[] connections) {
        super(neuronIndex, activationFunction, synapseDefaults, connections);
    }

}

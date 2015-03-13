package ch.brotzilla.neat.neuralnet;

import ch.brotzilla.neat.math.ActivationFunction;

public class OutputNeuron extends Neuron {

    @Override
    protected void setActivation(NeuralNet nn, double activation) {
        nn.setOutputNeuronActivation(neuronIndex, activation);
    }

    public OutputNeuron(int neuronIndex, ActivationFunction activationFunction, double[] synapseDefaults, Connection[] connections) {
        super(neuronIndex, activationFunction, synapseDefaults, connections);
    }

}

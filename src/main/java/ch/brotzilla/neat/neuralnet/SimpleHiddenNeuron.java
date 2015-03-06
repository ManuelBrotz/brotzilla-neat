package ch.brotzilla.neat.neuralnet;

import ch.brotzilla.neat.math.ActivationFunction;

public class SimpleHiddenNeuron extends SimpleNeuron {

    @Override
    protected void setActivation(NeuralNet nn, double activation) {
        nn.setHiddenNeuronActivation(neuronIndex, activation);
    }

    public SimpleHiddenNeuron(int neuronIndex, ActivationFunction activationFunction, Connection[] connections) {
        super(neuronIndex, activationFunction, connections);
    }

}

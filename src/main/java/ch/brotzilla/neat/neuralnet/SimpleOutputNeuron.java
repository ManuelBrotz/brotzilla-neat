package ch.brotzilla.neat.neuralnet;

import ch.brotzilla.neat.math.ActivationFunction;

public class SimpleOutputNeuron extends SimpleNeuron {

    @Override
    protected void setActivation(NeuralNet nn, double activation) {
        nn.setOutputNeuronActivation(neuronIndex, activation);
    }

    public SimpleOutputNeuron(int neuronIndex, ActivationFunction activationFunction, Connection[] connections) {
        super(neuronIndex, activationFunction, connections);
    }

}

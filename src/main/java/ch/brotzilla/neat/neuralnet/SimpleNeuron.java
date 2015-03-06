package ch.brotzilla.neat.neuralnet;

import com.google.common.base.Preconditions;

import ch.brotzilla.neat.math.ActivationFunction;

public abstract class SimpleNeuron extends Neuron {

    @Override
    void compute(NeuralNet nn) {
        double input = 0;
        for (final Connection c : connections) {
            input += c.getValue();
        }
        setActivation(nn, activationFunction.compute(input, null));
    }

    public SimpleNeuron(int neuronIndex, ActivationFunction activationFunction, Connection[] connections) {
        super(neuronIndex, activationFunction, connections);
        Preconditions.checkArgument(activationFunction.getNumberOfSynapses() == 0, "The number of synapses of the parameter 'activationFunction' has to be equal to zero");
    }

}

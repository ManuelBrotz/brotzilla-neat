package ch.brotzilla.neat.neuralnet;

import com.google.common.base.Preconditions;

import ch.brotzilla.neat.math.ActivationFunction;

public abstract class SimpleNeuron extends Neuron {

    @Override
    void compute() {
        double input = 0;
        for (final Connection c : connections) {
            Preconditions.checkState(c.getSynapse() == -1, "Simple neurons do not support additional synapses");
            input += c.getValue();
        }
        setActivation(activationFunction.compute(input, null));
    }

    public SimpleNeuron(NeuralNet owner, int neuronIndex, ActivationFunction activationFunction, Connection[] connections) {
        super(owner, neuronIndex, activationFunction, connections);
        Preconditions.checkArgument(activationFunction.getNumberOfSynapses() == 0, "The number of synapses of the parameter 'activationFunction' has to be equal to zero");
    }

}

package ch.brotzilla.neat.neuralnet;

import java.util.Arrays;

import com.google.common.base.Preconditions;

import ch.brotzilla.neat.math.ActivationFunction;

public abstract class ComplexNeuron extends Neuron {

    protected final double[] synapseDefaults;
    protected final double[] synapseActivations;

    public ComplexNeuron(NeuralNet owner, int neuronIndex, ActivationFunction activationFunction, double[] synapseDefaults, Connection[] connections) {
        super(owner, neuronIndex, activationFunction, connections);
        Preconditions.checkArgument(activationFunction.getNumberOfParameters() > 0, "The parameter 'activationFunction.getNumberOfParameters()' has to be greater than zero");
        Preconditions.checkNotNull(synapseDefaults, "The parameter 'synapseDefaults' must not be null");
        Preconditions.checkArgument(synapseDefaults.length == activationFunction.getNumberOfParameters(), "The length of the parameter 'synapseDefaults' has to be equal to " + activationFunction.getNumberOfParameters());
        this.synapseDefaults = Arrays.copyOf(synapseDefaults, synapseDefaults.length);
        this.synapseActivations = new double[activationFunction.getNumberOfParameters()];
}

    @Override
    public void compute() {
        double input = 0;
        for (int i = 0; i < synapseActivations.length; i++) {
            synapseActivations[i] = synapseDefaults[i];
        }
        for (final Connection c : connections) {
            final int synapse = c.getSynapse();
            if (synapse == -1) {
                input += c.getValue();
            } else {
                synapseActivations[synapse] += c.getValue();
            }
        }
        setActivation(activationFunction.compute(input, synapseActivations));
    }

}

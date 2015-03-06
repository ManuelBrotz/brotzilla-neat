package ch.brotzilla.neat.neuralnet;

import java.util.Arrays;

import com.google.common.base.Preconditions;

import ch.brotzilla.neat.math.ActivationFunction;

public abstract class ComplexNeuron extends Neuron {

    protected final SynapseConnection[] synapseConnections;
    protected final double[] synapseDefaults;
    protected final double[] synapseActivations;

    @Override
    void compute(NeuralNet nn) {
        double input = 0;
        for (int i = 0; i < synapseActivations.length; i++) {
            synapseActivations[i] = synapseDefaults[i];
        }
        for (final Connection c : connections) {
            input += c.getValue(nn);
        }
        for (final SynapseConnection c : synapseConnections) {
            synapseActivations[c.getSynapse()] += c.getValue(nn);
        }
        setActivation(nn, activationFunction.compute(input, synapseActivations));
    }

    public ComplexNeuron(int neuronIndex, ActivationFunction activationFunction, double[] synapseDefaults, Connection[] connections, SynapseConnection[] synapseConnections) {
        super(neuronIndex, activationFunction, connections);
        Preconditions.checkArgument(activationFunction.getNumberOfSynapses() > 0, "The number of synapses of the parameter 'activationFunction' has to be greater than zero");
        Preconditions.checkNotNull(synapseDefaults, "The parameter 'synapseDefaults' must not be null");
        Preconditions.checkArgument(synapseDefaults.length == activationFunction.getNumberOfSynapses(), "The length of the parameter 'synapseDefaults' has to be equal to " + activationFunction.getNumberOfSynapses());
        Preconditions.checkNotNull(synapseConnections, "The parameter 'synapseConnections' must not be null");
        for (int i = 0; i < synapseConnections.length; i++) {
            final SynapseConnection c = synapseConnections[i];
            Preconditions.checkNotNull(c, "The parameter 'synapseConnections[" + i + "]' must not be null");
            Preconditions.checkElementIndex(c.getSynapse(), activationFunction.getNumberOfSynapses(), "The synapse of the parameter 'synapseConnections[" + i + "]'");
        }
        this.synapseDefaults = Arrays.copyOf(synapseDefaults, synapseDefaults.length);
        this.synapseActivations = new double[activationFunction.getNumberOfSynapses()];
        this.synapseConnections = Arrays.copyOf(synapseConnections, synapseConnections.length);
    }

    public int getNumberOfSynapseConnections() {
        return synapseConnections.length;
    }
    
    public SynapseConnection getSynapseConnection(int index) {
        return synapseConnections[index];
    }
    
}

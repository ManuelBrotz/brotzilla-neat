package ch.brotzilla.neat.neuralnet;

import java.util.Arrays;

import com.google.common.base.Preconditions;

import ch.brotzilla.neat.Debug;
import ch.brotzilla.neat.math.ActivationFunction;

public abstract class Neuron {

    protected final int neuronIndex;
    protected final ActivationFunction activationFunction;
    protected final Connection[] connections;
    protected final double[] synapseDefaults;
    protected final double[] synapseInputs;
    
    protected abstract void setActivation(NeuralNet nn, double activation);

    void compute(NeuralNet nn) {
        for (int i = 0; i < synapseInputs.length; i++) {
            synapseInputs[i] = synapseDefaults[i];
        }
        for (final Connection c : connections) {
            synapseInputs[c.getSynapse()] += c.getValue(nn);
        }
        setActivation(nn, activationFunction.compute(synapseInputs));
    }

    public Neuron(int neuronIndex, ActivationFunction activationFunction, double[] synapseDefaults, Connection[] connections) {
        Preconditions.checkNotNull(activationFunction, "The parameter 'activationFunction' must not be null");
        Preconditions.checkNotNull(synapseDefaults, "The parameter 'synapseDefaults' must not be null");
        Preconditions.checkArgument(synapseDefaults.length == activationFunction.getNumberOfSynapses(), "The length of the parameter 'synapseDefaults' has to be equal to " + activationFunction.getNumberOfSynapses());
        Preconditions.checkNotNull(connections, "The parameter 'connections' must not be null");
        Preconditions.checkArgument(connections.length > 0, "The length of the parameter 'connections' has to be greater than zero");
        if (Debug.EnableIntegrityChecks) {
            for (int i = 0; i < connections.length; i++) {
                final Connection c = connections[i];
                Preconditions.checkNotNull(c, "The parameter 'connections[" + i + "]' must not be null");
                Preconditions.checkElementIndex(c.getSynapse(), activationFunction.getNumberOfSynapses(), "The synapse of the parameter 'connections[" + i + "]'");
            }
        }
        this.neuronIndex = neuronIndex;
        this.activationFunction = activationFunction;
        this.connections = Arrays.copyOf(connections, connections.length);
        this.synapseDefaults = Arrays.copyOf(synapseDefaults, synapseDefaults.length);
        this.synapseInputs = new double[activationFunction.getNumberOfSynapses()];
    }

    public int getNeuronIndex() {
        return neuronIndex;
    }
    
    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }
    
    public int getNumberOfConnections() {
        return connections.length;
    }
    
    public Connection getConnection(int index) {
        return connections[index];
    }

}

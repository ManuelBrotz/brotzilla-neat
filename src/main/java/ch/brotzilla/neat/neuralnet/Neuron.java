package ch.brotzilla.neat.neuralnet;

import java.util.Arrays;

import com.google.common.base.Preconditions;

import ch.brotzilla.neat.math.ActivationFunction;

public abstract class Neuron {

    protected final int neuronIndex;
    protected final ActivationFunction activationFunction;
    protected final Connection[] connections;
    
    protected abstract void setActivation(NeuralNet nn, double activation);

    abstract void compute(NeuralNet nn);

    public Neuron(int neuronIndex, ActivationFunction activationFunction, Connection[] connections) {
        Preconditions.checkNotNull(activationFunction, "The parameter 'activationFunction' must not be null");
        Preconditions.checkNotNull(connections, "The parameter 'connections' must not be null");
        Preconditions.checkArgument(connections.length > 0, "The length of the parameter 'connections' has to be greater than zero");
        for (int i = 0; i < connections.length; i++) {
            final Connection c = connections[i];
            Preconditions.checkNotNull(c, "The parameter 'connections[" + i + "]' must not be null");
        }
        this.neuronIndex = neuronIndex;
        this.activationFunction = activationFunction;
        this.connections = Arrays.copyOf(connections, connections.length);
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

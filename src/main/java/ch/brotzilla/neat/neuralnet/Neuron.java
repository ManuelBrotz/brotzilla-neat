package ch.brotzilla.neat.neuralnet;

import com.google.common.base.Preconditions;

import ch.brotzilla.neat.math.ActivationFunction;

public abstract class Neuron {

    protected final NeuralNet owner;
    protected final int neuronIndex;
    protected final ActivationFunction activationFunction;
    protected final Connection[] connections;
    
    public Neuron(NeuralNet owner, int neuronIndex, ActivationFunction activationFunction, Connection[] connections) {
        //TODO implement some checks on connections
        Preconditions.checkNotNull(owner, "The parameter 'owner' must not be null");
        Preconditions.checkNotNull(activationFunction, "The parameter 'activationFunction' must not be null");
        Preconditions.checkNotNull(connections, "The parameter 'connections' must not be null");
        Preconditions.checkArgument(connections.length > 0, "The length of the parameter 'connections' has to be greater than zero");
        this.owner = owner;
        this.neuronIndex = neuronIndex;
        this.activationFunction = activationFunction;
        this.connections = connections;
    }

    public NeuralNet getOwner() {
        return owner;
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
    
    public abstract void setActivation(double activation);
    
    public abstract void compute();

}

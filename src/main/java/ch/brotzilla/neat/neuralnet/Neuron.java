package ch.brotzilla.neat.neuralnet;

import com.google.common.base.Preconditions;

import ch.brotzilla.neat.math.ActivationFunction;

public abstract class Neuron {

    protected final NeuralNet owner;
    protected final int neuronIndex;
    protected final ActivationFunction activationFunction;
    protected final Connection[] connections;
    
    protected abstract void setActivation(double activation);

    abstract void compute();

    public Neuron(NeuralNet owner, int neuronIndex, ActivationFunction activationFunction, Connection[] connections) {
        Preconditions.checkNotNull(owner, "The parameter 'owner' must not be null");
        Preconditions.checkNotNull(activationFunction, "The parameter 'activationFunction' must not be null");
        Preconditions.checkNotNull(connections, "The parameter 'connections' must not be null");
        Preconditions.checkArgument(connections.length > 0, "The length of the parameter 'connections' has to be greater than zero");
        for (int i = 0; i < connections.length; i++) {
            final Connection c = connections[i];
            Preconditions.checkNotNull(c, "The parameter 'connections[" + i + "]' must not be null");
            Preconditions.checkArgument(c.getSynapse() >= -1, "The synapse of the parameter 'connections[" + i + "]' has to be greater than or equal to -1");
            if (c.getSynapse() > -1) {
                Preconditions.checkElementIndex(c.getSynapse(), activationFunction.getNumberOfSynapses(), "The synapse of the parameter 'connections[" + i + "]'");
            }
        }
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

}

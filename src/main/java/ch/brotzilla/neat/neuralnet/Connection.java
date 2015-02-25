package ch.brotzilla.neat.neuralnet;

import com.google.common.base.Preconditions;

public class Connection {

    private final NeuralNet owner;
    private Neuron inputNeuron, outputNeuron;
    private int synapse;
    private double weight;

    public Connection(NeuralNet owner) {
        Preconditions.checkNotNull(owner, "The parameter 'owner' must not be null");
        this.owner = owner;
    }
    
    public NeuralNet getOwner() {
        return owner;
    }

    public Neuron getInputNeuron() {
        return inputNeuron;
    }
   
    public Neuron getOutputNeuron() {
        return outputNeuron;
    }
   
    public int getSynapse() {
        return synapse;
    }
   
    public double getWeight() {
        return weight;
    }

}

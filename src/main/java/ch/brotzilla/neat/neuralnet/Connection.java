package ch.brotzilla.neat.neuralnet;

import com.google.common.base.Preconditions;

public class Connection {

    private final NeuralNet owner;
    private int innovationNumber;
    private Neuron inputNeuron;
    private BasicNeuron outputNeuron;
    private int synapse;
    private double weight;

    public Connection(NeuralNet owner, Neuron inputNeuron, BasicNeuron outputNeuron) {
        Preconditions.checkNotNull(owner, "The parameter 'owner' must not be null");
        Preconditions.checkNotNull(inputNeuron, "The parameter 'inputNeuron' must not be null");
        Preconditions.checkNotNull(outputNeuron, "The parameter 'outputNeuron' must not be null");
        this.owner = owner;
        this.inputNeuron = inputNeuron;
        this.outputNeuron = outputNeuron;
    }
    
    public NeuralNet getOwner() {
        return owner;
    }

    public int getInnovationNumber() {
        return innovationNumber;
    }
    
    public void setInnovationNumber(int value) {
        Preconditions.checkArgument(value >= 0, "The parameter 'value' has to be greater than or equal to zero");
        innovationNumber = value;
    }
    
    public Neuron getInputNeuron() {
        return inputNeuron;
    }
    
    public void setInputNeuron(Neuron value) {
        Preconditions.checkNotNull(value, "The parameter 'value' must not be null");
        inputNeuron = value;
    }
   
    public BasicNeuron getOutputNeuron() {
        return outputNeuron;
    }
    
    public void setOutputNeuron(BasicNeuron value) {
        Preconditions.checkNotNull(value, "The parameter 'value' must not be null");
        outputNeuron = value;
    }
   
    public int getSynapse() {
        return synapse;
    }
    
    public void setSynapse(int value) {
        Preconditions.checkArgument(value >= 0, "The parameter 'value' has to be greater than or equal to zero");
        synapse = value;
    }
   
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double value) {
        weight = value;
    }

}

package ch.brotzilla.neat.neuralnet;

import com.google.common.base.Preconditions;

public class Connection {

    private final int innovationNumber, inputNode, outputNode;
    private int synapse;
    private double weight;

    public Connection(int innovationNumber, int inputNode, int outputNode) {
        Preconditions.checkArgument(innovationNumber > 0, "The parameter 'innovationNumber' has to be greater than zero");
        Preconditions.checkArgument(inputNode > 0, "The parameter 'inputNode' has to be greater than zero");
        Preconditions.checkArgument(outputNode > 0, "The parameter 'outputNode' has to be greater than zero");
        this.innovationNumber = innovationNumber;
        this.inputNode = inputNode;
        this.outputNode = outputNode;
    }
    
    public int getInnovationNumber() {
        return innovationNumber;
    }

    public int getInputNode() {
        return inputNode;
    }
    
    public int getOutputNode() {
        return outputNode;
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

package ch.brotzilla.neat.neuralnet;

import com.google.common.base.Preconditions;

public class Link {

    private final int innovationNumber, sourceNode, targetNode;
    private int targetSynapse;
    private double weight;

    public Link(int innovationNumber, int sourceNode, int targetNode, int targetSynapse) {
        Preconditions.checkArgument(innovationNumber > 0, "The parameter 'innovationNumber' has to be greater than zero");
        Preconditions.checkArgument(sourceNode > 0, "The parameter 'sourceNode' has to be greater than zero");
        Preconditions.checkArgument(targetNode > 0, "The parameter 'targetNode' has to be greater than zero");
        Preconditions.checkArgument(targetSynapse >= 0, "The parameter 'targetSynapse' has to be greater than or equal to zero");
        this.innovationNumber = innovationNumber;
        this.sourceNode = sourceNode;
        this.targetNode = targetNode;
    }
    
    public int getInnovationNumber() {
        return innovationNumber;
    }

    public int getSourceNode() {
        return sourceNode;
    }
    
    public int getTargetNode() {
        return targetNode;
    }
   
    public int getTargetSynapse() {
        return targetSynapse;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double value) {
        weight = value;
    }
    
    @Override
    public int hashCode() {
        return innovationNumber;
    }
    
}

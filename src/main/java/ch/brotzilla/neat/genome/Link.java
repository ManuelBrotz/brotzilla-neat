package ch.brotzilla.neat.genome;

import com.google.common.base.Preconditions;

public class Link {

    private final int innovationNumber, sourceNode, targetNode, targetSynapse;
    private double weight;

    public Link(int innovationNumber, int sourceNode, int targetNode, int targetSynapse) {
        Preconditions.checkArgument(innovationNumber > 0, "The parameter 'innovationNumber' has to be greater than zero");
        Preconditions.checkArgument(sourceNode > 0, "The parameter 'sourceNode' has to be greater than zero");
        Preconditions.checkArgument(targetNode > 0, "The parameter 'targetNode' has to be greater than zero");
        Preconditions.checkArgument(targetSynapse >= 0, "The parameter 'targetSynapse' has to be greater than or equal to zero");
        this.innovationNumber = innovationNumber;
        this.sourceNode = sourceNode;
        this.targetNode = targetNode;
        this.targetSynapse = targetSynapse;
    }
    
    public Link(Link source) {
        Preconditions.checkNotNull(source, "The parameter 'source' must not be null");
        innovationNumber = source.innovationNumber;
        sourceNode = source.sourceNode;
        targetNode = source.targetNode;
        targetSynapse = source.targetSynapse;
        weight = source.weight;
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
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Link) {
            final Link link = (Link) other;
            return innovationNumber == link.innovationNumber
                    && sourceNode == link.sourceNode
                    && targetNode == link.targetNode
                    && targetSynapse == link.targetSynapse
                    && weight == link.weight;
        }
        return false;
    }
    
    @Override
    public Link clone() {
        return new Link(this);
    }
    
}

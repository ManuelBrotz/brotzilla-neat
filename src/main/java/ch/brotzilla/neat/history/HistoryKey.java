package ch.brotzilla.neat.history;

import com.google.common.base.Preconditions;

public abstract class HistoryKey {

    private boolean hashed = false;
    private int hashCode;

    protected final int sourceNode, targetNode, targetSynapse;

    protected abstract int computeHash();

    public HistoryKey(int sourceNode, int targetNode, int targetSynapse) {
        Preconditions.checkArgument(sourceNode > 0, "The parameter 'sourceNode' has to be greater than zero");
        Preconditions.checkArgument(targetNode > 0, "The parameter 'targetNode' has to be greater than zero");
        Preconditions.checkArgument(targetSynapse >= 0, "The parameter 'targetSynapse' has to be greater than or equal to zero");
        this.sourceNode = sourceNode;
        this.targetNode = targetNode;
        this.targetSynapse = targetSynapse;
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

    @Override
    public int hashCode() {
        if (hashed) {
            return hashCode;
        }
        hashCode = computeHash();
        hashed = true;
        return hashCode;
    }
    
    @Override
    public abstract boolean equals(Object other);

}

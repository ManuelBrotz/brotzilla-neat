package ch.brotzilla.neat.evolution;

import java.nio.charset.Charset;

import com.google.common.base.Preconditions;

public class NodeHistoryKey extends HistoryKey {

    private final String activationFunctionID;
    
    @Override
    protected int computeHash() {
        return InnovationList.hashFunction.newHasher()
                .putInt(sourceNode).putInt(targetNode).putInt(targetSynapse)
                .putString(activationFunctionID, Charset.defaultCharset())
                .hash().asInt();
    }

    public NodeHistoryKey(int sourceNode, int targetNode, int targetSynapse, String activationFunctionID) {
        super(sourceNode, targetNode, targetSynapse);
        Preconditions.checkNotNull(activationFunctionID, "The parameter 'activationFunctionID' must not be null");
        Preconditions.checkArgument(!activationFunctionID.trim().isEmpty(), "The parameter 'activationFunctionID' must not be empty");
        this.activationFunctionID = activationFunctionID;
    }
    
    public String getActivationFunctionID() {
        return activationFunctionID;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof NodeHistoryKey) {
            final NodeHistoryKey otherKey = (NodeHistoryKey) other;
            return sourceNode == otherKey.sourceNode 
                    && targetNode == otherKey.targetNode
                    && targetSynapse == otherKey.targetSynapse
                    && activationFunctionID.equals(otherKey.activationFunctionID);
        }
        return false;
    }

}

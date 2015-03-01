package ch.brotzilla.neat.history;

import com.google.common.base.Preconditions;

public class NodeInnovation {
    
    private final int sourceLinkInnovationNumber, targetLinkInnovationNumber, nodeInnovationNumber;
    private final LinkHistoryKey sourceLinkHistoryKey, targetLinkHistoryKey;
    private final String activationFunctionID;
    
    public NodeInnovation(int nodeInnovationNumber, int sourceLinkInnovationNumber, int targetLinkInnovationNumber, LinkHistoryKey sourceLinkHistoryKey, LinkHistoryKey targetLinkHistoryKey, String activationFunctionID) {
        Preconditions.checkArgument(nodeInnovationNumber > 0, "The paramter 'nodeInnovationNumber' has to be greater than zero");
        Preconditions.checkArgument(sourceLinkInnovationNumber > 0, "The paramter 'sourceLinkInnovationNumber' has to be greater than zero");
        Preconditions.checkArgument(targetLinkInnovationNumber > 0, "The paramter 'targetLinkInnovationNumber' has to be greater than zero");
        
        Preconditions.checkNotNull(sourceLinkHistoryKey, "The parameter 'sourceLinkHistoryKey' must not be null");
        Preconditions.checkArgument(sourceLinkHistoryKey.getTargetNode() == nodeInnovationNumber, "The target node of the parameter 'sourceLinkHistoryKey' has to be equal to " + nodeInnovationNumber);
        Preconditions.checkArgument(sourceLinkHistoryKey.getTargetSynapse() == 0, "The target synapse of the parameter 'sourceLinkHistoryKey' has to be equal to zero");
        
        Preconditions.checkNotNull(targetLinkHistoryKey, "The parameter 'targetLinkHistoryKey' must not be null");
        Preconditions.checkArgument(targetLinkHistoryKey.getSourceNode() == nodeInnovationNumber, "The source node of the parameter 'targetLinkHistoryKey' has to be equal to " + nodeInnovationNumber);
        
        Preconditions.checkNotNull(activationFunctionID, "The parameter 'activationFunctionID' must not be null");
        Preconditions.checkArgument(!activationFunctionID.trim().isEmpty(), "The parameter 'activationFunctionID' must not be empty");
        
        this.nodeInnovationNumber = nodeInnovationNumber;
        this.targetLinkHistoryKey = targetLinkHistoryKey;
        this.targetLinkInnovationNumber = targetLinkInnovationNumber;
        this.sourceLinkHistoryKey = sourceLinkHistoryKey;
        this.sourceLinkInnovationNumber = sourceLinkInnovationNumber;
        this.activationFunctionID = activationFunctionID;
    }
    
    public int getNodeInnovationNumber() {
        return nodeInnovationNumber;
    }

    public int getSourceLinkInnovationNumber() {
        return sourceLinkInnovationNumber;
    }

    public int getTargetLinkInnovationNumber() {
        return targetLinkInnovationNumber;
    }
    
    public LinkHistoryKey getSourceLinkHistoryKey() {
        return sourceLinkHistoryKey;
    }
    
    public LinkHistoryKey getTargetLinkHistoryKey() {
        return targetLinkHistoryKey;
    }
    
    public String getActivationFunctionID() {
        return activationFunctionID;
    }
    
}
package ch.brotzilla.neat.history;

import com.google.common.base.Preconditions;

public class NodeInnovation {
    
    private final int nodeInnovationNumber;
    private final NodeHistoryKey nodeHistoryKey;
    private final LinkInnovation sourceLinkInnovation, targetLinkInnovation;
    
    public NodeInnovation(int nodeInnovationNumber, NodeHistoryKey nodeHistoryKey, LinkInnovation sourceLinkInnovation, LinkInnovation targetLinkInnovation) {
        Preconditions.checkArgument(nodeInnovationNumber > 0, "The paramter 'nodeInnovationNumber' has to be greater than zero");
        
        Preconditions.checkNotNull(nodeHistoryKey, "The parameter 'nodeHistoryKey' must not be null");
        
        Preconditions.checkNotNull(sourceLinkInnovation, "The parameter 'sourceLinkInnovation' must not be null");
        Preconditions.checkArgument(sourceLinkInnovation.getSourceNode() == nodeHistoryKey.getSourceNode(), "The source node of the parameter 'sourceLinkInnovation' has to be equal to " + nodeHistoryKey.getSourceNode());
        Preconditions.checkArgument(sourceLinkInnovation.getTargetNode() == nodeInnovationNumber, "The target node of the parameter 'sourceLinkInnovation' has to be equal to " + nodeInnovationNumber);
        Preconditions.checkArgument(sourceLinkInnovation.getTargetSynapse() == 0, "The target synapse of the parameter 'sourceLinkInnovation' has to be equal to zero");
        
        Preconditions.checkNotNull(targetLinkInnovation, "The parameter 'targetLinkInnovation' must not be null");
        Preconditions.checkArgument(targetLinkInnovation.getSourceNode() == nodeInnovationNumber, "The source node of the parameter 'targetLinkInnovation' has to be equal to " + nodeInnovationNumber);
        Preconditions.checkArgument(targetLinkInnovation.getTargetNode() == nodeHistoryKey.getTargetNode(), "The target node of the parameter 'targetLinkInnovation' has to be equal to " + nodeHistoryKey.getTargetNode());
        Preconditions.checkArgument(targetLinkInnovation.getTargetSynapse() == nodeHistoryKey.getTargetSynapse(), "The target synapse of the parameter 'targetLinkInnovation' has to be equal to " + nodeHistoryKey.getTargetSynapse());
        
        this.nodeInnovationNumber = nodeInnovationNumber;
        this.nodeHistoryKey = nodeHistoryKey;
        this.sourceLinkInnovation = sourceLinkInnovation;
        this.targetLinkInnovation = targetLinkInnovation;
    }
    
    public int getNodeInnovationNumber() {
        return nodeInnovationNumber;
    }
    
    public int getSourceLinkInnovationNumber() {
        return sourceLinkInnovation.getLinkInnovationNumber();
    }

    public int getTargetLinkInnovationNumber() {
        return targetLinkInnovation.getLinkInnovationNumber();
    }
    
    public String getActivationFunctionID() {
        return nodeHistoryKey.getActivationFunctionID();
    }
    
    public int getSourceNode() {
        return nodeHistoryKey.getSourceNode();
    }
    
    public int getTargetNode() {
        return nodeHistoryKey.getTargetNode();
    }
    
    public int getTargetSynapse() {
        return nodeHistoryKey.getTargetSynapse();
    }

    public NodeHistoryKey getNodeHistoryKey() {
        return nodeHistoryKey;
    }

    public LinkInnovation getSourceLinkInnovation() {
        return sourceLinkInnovation;
    }
    
    public LinkInnovation getTargetLinkInnovation() {
        return targetLinkInnovation;
    }
    
}
package ch.brotzilla.neat.history;

import com.google.common.base.Preconditions;

public class LinkInnovation {
    
    private final int linkInnovationNumber;
    private final LinkHistoryKey linkHistoryKey;
    
    public LinkInnovation(int linkInnovationNumber, LinkHistoryKey linkHistoryKey) {
        Preconditions.checkNotNull(linkHistoryKey, "The parameter 'linkHistoryKey' must not be null");
        Preconditions.checkArgument(linkInnovationNumber > 0, "The parameter 'linkInnovationNumber' has to be greater than zero");
        this.linkInnovationNumber = linkInnovationNumber;
        this.linkHistoryKey = linkHistoryKey;
    }
    
    public int getLinkInnovationNumber() {
        return linkInnovationNumber;
    }
    
    public LinkHistoryKey getLinkHistoryKey() {
        return linkHistoryKey;
    }
    
    public int getSourceNode() {
        return linkHistoryKey.getSourceNode();
    }
    
    public int getTargetNode() {
        return linkHistoryKey.getTargetNode();
    }
    
    public int getTargetSynapse() {
        return linkHistoryKey.getTargetSynapse();
    }
    
}
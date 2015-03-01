package ch.brotzilla.neat.evolution;

import com.google.common.base.Preconditions;

public class LinkInnovation {
    
    private final int linkInnovationNumber;
    
    public LinkInnovation(int linkInnovationNumber) {
        Preconditions.checkArgument(linkInnovationNumber > 0, "The parameter 'linkInnovationNumber' has to be greater than zero");
        this.linkInnovationNumber = linkInnovationNumber;
    }
    
    public int getLinkInnovationNumber() {
        return linkInnovationNumber;
    }
    
}
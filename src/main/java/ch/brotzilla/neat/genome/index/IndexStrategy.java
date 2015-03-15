package ch.brotzilla.neat.genome.index;

import ch.brotzilla.neat.genome.Link;
import ch.brotzilla.neat.genome.Node;

public interface IndexStrategy {

    boolean indexBiasNode();
    
    boolean indexInputNodes();
    
    boolean indexHiddenNodes();
    
    boolean indexOutputNodes();
    
    boolean indexLinks();
    
    int nextBiasIndex(Node node);
    
    int nextInputIndex(Node node);
    
    int nextHiddenIndex(Node node);
    
    int nextOutputIndex(Node node);
    
    int nextLinkIndex(Link link);
}

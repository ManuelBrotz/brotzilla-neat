package ch.brotzilla.neat.genome.index;

import ch.brotzilla.neat.genome.Link;
import ch.brotzilla.neat.genome.Node;

public class LayeredIndexStrategy implements IndexStrategy {

    private int nextInputIndex, nextHiddenIndex, nextOutputIndex;
    
    public LayeredIndexStrategy() {}

    public boolean indexBiasNode() {
        return false;
    }

    public boolean indexInputNodes() {
        return true;
    }

    public boolean indexHiddenNodes() {
        return true;
    }

    public boolean indexOutputNodes() {
        return true;
    }

    public boolean indexLinks() {
        return false;
    }

    public int nextBiasIndex(Node node) {
        throw new UnsupportedOperationException();
    }

    public int nextInputIndex(Node node) {
        return nextInputIndex++;
    }

    public int nextHiddenIndex(Node node) {
        return nextHiddenIndex++;
    }

    public int nextOutputIndex(Node node) {
        return nextOutputIndex++;
    }

    public int nextLinkIndex(Link link) {
        throw new UnsupportedOperationException();
    }

}

package ch.brotzilla.neat.genome.index;

import gnu.trove.impl.Constants;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import ch.brotzilla.neat.Debug;
import ch.brotzilla.neat.genome.Genome;
import ch.brotzilla.neat.genome.Link;
import ch.brotzilla.neat.genome.Node;

import com.google.common.base.Preconditions;

public class NodeIndex {

    private final TIntIntMap indexMap;

    private void indexBiasNode(TIntIntMap indexMap, Genome genome, IndexStrategy strategy) {
        final Node biasNode = genome.getBiasNode();
        if (strategy.indexBiasNode() && biasNode != null) {
            if (Debug.EnableIntegrityChecks) {
                Preconditions.checkState(!indexMap.containsKey(biasNode.getInnovationNumber()), "Internal Error: The index already contains a node with the innovation number " + biasNode.getInnovationNumber());
            }
            indexMap.put(biasNode.getInnovationNumber(), strategy.nextBiasIndex(biasNode));
        }
    }
    
    private void indexInputNodes(TIntIntMap indexMap, Genome genome, IndexStrategy strategy) {
        if (strategy.indexInputNodes()) {
            for (final Node node : genome.getInputNodes()) {
                if (Debug.EnableIntegrityChecks) {
                    Preconditions.checkState(!indexMap.containsKey(node.getInnovationNumber()), "Internal Error: The index already contains a node with the innovation number " + node.getInnovationNumber());
                }
                indexMap.put(node.getInnovationNumber(), strategy.nextInputIndex(node));
            }
        }
    }
    
    private void indexHiddenNodes(TIntIntMap indexMap, Genome genome, IndexStrategy strategy) {
        if (strategy.indexHiddenNodes()) {
            for (final Node node : genome.getHiddenNodes()) {
                if (Debug.EnableIntegrityChecks) {
                    Preconditions.checkState(!indexMap.containsKey(node.getInnovationNumber()), "Internal Error: The index already contains a node with the innovation number " + node.getInnovationNumber());
                }
                indexMap.put(node.getInnovationNumber(), strategy.nextHiddenIndex(node));
            }
        }
    }
    
    private void indexOutputNodes(TIntIntMap indexMap, Genome genome, IndexStrategy strategy) {
        if (strategy.indexOutputNodes()) {
            for (final Node node : genome.getOutputNodes()) {
                if (Debug.EnableIntegrityChecks) {
                    Preconditions.checkState(!indexMap.containsKey(node.getInnovationNumber()), "Internal Error: The index already contains a node with the innovation number " + node.getInnovationNumber());
                }
                indexMap.put(node.getInnovationNumber(), strategy.nextOutputIndex(node));
            }
        }
    }
    
    private void indexLinks(TIntIntMap indexMap, Genome genome, IndexStrategy strategy) {
        if (strategy.indexLinks()) {
            for (final Link link : genome.getLinks()) {
                if (Debug.EnableIntegrityChecks) {
                    Preconditions.checkState(!indexMap.containsKey(link.getInnovationNumber()), "Internal Error: The index already contains a link with the innovation number " + link.getInnovationNumber());
                }
                indexMap.put(link.getInnovationNumber(), strategy.nextLinkIndex(link));
            }
        }
    }
    
    private TIntIntMap createIndex(Genome genome, IndexStrategy strategy) {
        final TIntIntMap result = new TIntIntHashMap(Constants.DEFAULT_CAPACITY, Constants.DEFAULT_LOAD_FACTOR, 0, -1);
        indexBiasNode(result, genome, strategy);
        indexInputNodes(result, genome, strategy);
        indexHiddenNodes(result, genome, strategy);
        indexOutputNodes(result, genome, strategy);
        indexLinks(result, genome, strategy);
        return result;
    }

    public NodeIndex(Genome genome, IndexStrategy strategy) {
        Preconditions.checkNotNull(genome, "The parameter 'genome' must not be null");
        Preconditions.checkNotNull(strategy, "The parameter 'strategy' must not be null");
        indexMap = createIndex(genome, strategy);
    }

    public int getNodeIndex(int innovationNumber) {
        return indexMap.get(innovationNumber);
    }

}

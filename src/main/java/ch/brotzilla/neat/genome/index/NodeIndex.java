package ch.brotzilla.neat.genome.index;

import gnu.trove.impl.Constants;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import ch.brotzilla.neat.Debug;
import ch.brotzilla.neat.genome.Genome;
import ch.brotzilla.neat.genome.Node;

import com.google.common.base.Preconditions;

public class NodeIndex {
    
    private final TIntIntMap nodeIndexMap;

    private void createNodeIndex(Genome genome) {
        Preconditions.checkNotNull(genome, "The parameter 'genome' must not be null");
        int index = 0;
        for (final Node node : genome.getInputNodes()) {
            if (Debug.EnableIntegrityChecks) {
                Preconditions.checkState(!nodeIndexMap.containsKey(node.getInnovationNumber()), "Internal Error: The index already contains a node with the innovation number " + node.getInnovationNumber());
            }
            nodeIndexMap.put(node.getInnovationNumber(), index++);
        }
        index = 0;
        for (final Node node : genome.getHiddenNodes()) {
            if (Debug.EnableIntegrityChecks) {
                Preconditions.checkState(!nodeIndexMap.containsKey(node.getInnovationNumber()), "Internal Error: The index already contains a node with the innovation number " + node.getInnovationNumber());
            }
            nodeIndexMap.put(node.getInnovationNumber(), index++);
        }
        index = 0;
        for (final Node node : genome.getOutputNodes()) {
            if (Debug.EnableIntegrityChecks) {
                Preconditions.checkState(!nodeIndexMap.containsKey(node.getInnovationNumber()), "Internal Error: The index already contains a node with the innovation number " + node.getInnovationNumber());
            }
            nodeIndexMap.put(node.getInnovationNumber(), index++);
        }
    }
    
    public NodeIndex(Genome genome) {
        nodeIndexMap = new TIntIntHashMap(Constants.DEFAULT_CAPACITY, Constants.DEFAULT_LOAD_FACTOR, 0, -1);
        createNodeIndex(genome);
    }

    public int getNodeIndex(int innovationNumber) {
        return nodeIndexMap.get(innovationNumber);
    }

}

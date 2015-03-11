package ch.brotzilla.neat.genome;

import gnu.trove.impl.Constants;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import ch.brotzilla.neat.Debug;

import com.google.common.base.Preconditions;

public class GenomeIndex {
    
    private final TIntIntMap innovationIndexMap;
    
    public GenomeIndex() {
        innovationIndexMap = new TIntIntHashMap(Constants.DEFAULT_CAPACITY, Constants.DEFAULT_LOAD_FACTOR, 0, -1);
    }

    public int getInnovationIndex(int innovationNumber) {
        return innovationIndexMap.get(innovationNumber);
    }
    
    public void createInnovationIndex(Genome genome) {
        Preconditions.checkNotNull(genome, "The parameter 'genome' must not be null");
        innovationIndexMap.clear();
        int index = 0;
        for (final Node node : genome.getInputNodes()) {
            if (Debug.EnableIntegrityChecks) {
                Preconditions.checkState(!innovationIndexMap.containsKey(node.getInnovationNumber()), "Internal Error: The index already contains a node with the innovation number " + node.getInnovationNumber());
            }
            innovationIndexMap.put(node.getInnovationNumber(), index++);
        }
        index = 0;
        for (final Node node : genome.getHiddenNodes()) {
            if (Debug.EnableIntegrityChecks) {
                Preconditions.checkState(!innovationIndexMap.containsKey(node.getInnovationNumber()), "Internal Error: The index already contains a node with the innovation number " + node.getInnovationNumber());
            }
            innovationIndexMap.put(node.getInnovationNumber(), index++);
        }
        index = 0;
        for (final Node node : genome.getOutputNodes()) {
            if (Debug.EnableIntegrityChecks) {
                Preconditions.checkState(!innovationIndexMap.containsKey(node.getInnovationNumber()), "Internal Error: The index already contains a node with the innovation number " + node.getInnovationNumber());
            }
            innovationIndexMap.put(node.getInnovationNumber(), index++);
        }
        index = 0;
        for (final Link link : genome.getLinks()) {
            if (Debug.EnableIntegrityChecks) {
                Preconditions.checkState(!innovationIndexMap.containsKey(link.getInnovationNumber()), "Internal Error: The index already contains a link with the innovation number " + link.getInnovationNumber());
            }
            innovationIndexMap.put(link.getInnovationNumber(), index++);
        }
    }

}

package ch.brotzilla.neat.expression;

import gnu.trove.impl.Constants;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import ch.brotzilla.neat.Debug;
import ch.brotzilla.neat.genome.Genome;
import ch.brotzilla.neat.genome.Link;
import ch.brotzilla.neat.genome.Node;

import com.google.common.base.Preconditions;

public class GenomeIndex {
    
	private static final int[] NONE = new int[0];
	
    private final TIntIntMap innovationIndexMap;
    private int[] hiddenNeuronConnections, outputNeuronConnections;
    private int[] hiddenNeuronSynapseConnections, outputNeuronSynapseConnections;
    
    private void createNodeIndex(Genome genome) {
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
    }
    
    private void createLinkIndex(Genome genome) {
        int index = 0;
        for (final Link link : genome.getLinks()) {
            if (Debug.EnableIntegrityChecks) {
                Preconditions.checkState(!innovationIndexMap.containsKey(link.getInnovationNumber()), "Internal Error: The index already contains a link with the innovation number " + link.getInnovationNumber());
            }
            innovationIndexMap.put(link.getInnovationNumber(), index++);
            final int targetNodeIndex = innovationIndexMap.get(link.getTargetNode());
            Preconditions.checkState(targetNodeIndex >= 0, "Internal Error: No index found for innovation number " + link.getTargetNode());
        }
    }
    
    public GenomeIndex() {
        innovationIndexMap = new TIntIntHashMap(Constants.DEFAULT_CAPACITY, Constants.DEFAULT_LOAD_FACTOR, 0, -1);
        clear();
    }

    public int getInnovationIndex(int innovationNumber) {
        return innovationIndexMap.get(innovationNumber);
    }
    
    public int getHiddenNeuronConnections(int neuronIndex) {
    	return hiddenNeuronConnections[neuronIndex];
    }
    
    public int getHiddenNeuronSynapseConnections(int neuronIndex) {
    	return hiddenNeuronSynapseConnections[neuronIndex];
    }
    
    public int getOutputNeuronConnections(int neuronIndex) {
    	return outputNeuronConnections[neuronIndex];
    }
    
    public int getOutputNeuronSynapseConnections(int neuronIndex) {
    	return outputNeuronSynapseConnections[neuronIndex];
    }
    
    public void clear() {
    	innovationIndexMap.clear();
    	hiddenNeuronConnections = NONE;
    	hiddenNeuronSynapseConnections = NONE;
    	outputNeuronConnections = NONE;
    	outputNeuronSynapseConnections = NONE;
    }
    
    public void createIndex(Genome genome) {
        Preconditions.checkNotNull(genome, "The parameter 'genome' must not be null");
        clear();
        createNodeIndex(genome);
        createLinkIndex(genome);
    }

}

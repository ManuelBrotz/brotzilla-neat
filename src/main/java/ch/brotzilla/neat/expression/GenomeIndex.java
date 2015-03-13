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
	
    private final TIntIntMap nodeIndexMap;
    private int[] hiddenNeuronConnections, outputNeuronConnections;
    private int[] hiddenNeuronSynapseConnections, outputNeuronSynapseConnections;
    
    private void createNodeIndex(Genome genome) {
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
    
    private void createLinkIndex(Genome genome) {
        for (final Link link : genome.getLinks()) {
            final int targetNode = link.getTargetNode();
            final int targetNodeIndex = nodeIndexMap.get(targetNode);
            Preconditions.checkState(targetNodeIndex >= 0, "Internal Error: No index found for innovation number " + targetNode);
        }
    }
    
    public GenomeIndex() {
        nodeIndexMap = new TIntIntHashMap(Constants.DEFAULT_CAPACITY, Constants.DEFAULT_LOAD_FACTOR, 0, -1);
        clear();
    }

    public int getNodeIndex(int innovationNumber) {
        return nodeIndexMap.get(innovationNumber);
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
    	nodeIndexMap.clear();
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

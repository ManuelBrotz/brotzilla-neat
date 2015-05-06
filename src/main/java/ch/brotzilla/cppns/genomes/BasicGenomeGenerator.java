package ch.brotzilla.cppns.genomes;

import com.google.common.base.Preconditions;

import ch.brotzilla.cppns.images.ImageType;
import ch.brotzilla.neat.evolution.Rng;
import ch.brotzilla.neat.genome.Genome;
import ch.brotzilla.neat.genome.Link;
import ch.brotzilla.neat.genome.Node;
import ch.brotzilla.neat.genome.NodeType;
import ch.brotzilla.neat.history.HistoryList;
import ch.brotzilla.neat.history.LinkInnovation;
import ch.brotzilla.neat.math.ActivationFunction;

public class BasicGenomeGenerator extends AbstractGenomeGenerator {

    private int minHiddenNodes = 1, maxHiddenNodes = 3, minLinksPerNode = 1, maxLinksPerNode = 6;
    private double recurrentLinkProbability = 0.1, synapseLinkProbability = 0.1;
    
    public BasicGenomeGenerator(HistoryList historyList, ActivationFunctionProvider activationFunctionProvider, ActivationFunction outputActivationFunction) {
        super(historyList, activationFunctionProvider, outputActivationFunction);
    }
    
    public BasicGenomeGenerator(HistoryList historyList, ActivationFunctionProvider activationFunctionProvider) {
        super(historyList, activationFunctionProvider, null);
    }
    
    public int getMinHiddenNodes() {
        return minHiddenNodes;
    }
    
    public void setMinHiddenNodes(int value) {
        Preconditions.checkArgument(value > 0, "The parameter 'value' has to be greater than zero");
        minHiddenNodes = value;
    }

    public int getMaxHiddenNodes() {
        return maxHiddenNodes;
    }
    
    public void setMaxHiddenNodes(int value) {
        Preconditions.checkArgument(value > minHiddenNodes, "The parameter 'value' has to be greater than " + minHiddenNodes);
        maxHiddenNodes = value;
    }
    
    public void setMinMaxHiddenNodes(int min, int max) {
        Preconditions.checkArgument(min > 0, "The parameter 'min' has to be greater than zero");
        Preconditions.checkArgument(max > minHiddenNodes, "The parameter 'max' has to be greater than " + min);
        minHiddenNodes = min;
        maxHiddenNodes = max;
    }
    
    public int getMinLinksPerNode() {
        return minLinksPerNode;
    }
    
    public void setMinLinksPerNode(int value) {
        Preconditions.checkArgument(value > 0, "The parameter 'value' has to be greater than zero");
        minLinksPerNode = value;
    }

    public int getMaxLinksPerNode() {
        return maxLinksPerNode;
    }
    
    public void setMaxLinksPerNode(int value) {
        Preconditions.checkArgument(value > minLinksPerNode, "The parameter 'value' has to be greater than " + minLinksPerNode);
        maxLinksPerNode = value;
    }
    
    public void setMinMaxLinksPerNode(int min, int max) {
        Preconditions.checkArgument(min > 0, "The parameter 'min' has to be greater than zero");
        Preconditions.checkArgument(max > minLinksPerNode, "The parameter 'max' has to be greater than " + min);
        minLinksPerNode = min;
        maxLinksPerNode = max;
    }
    
    public double getRecurrentLinkProbability() {
        return recurrentLinkProbability;
    }
    
    public void setRecurrentLinkProbability(double value) {
        Preconditions.checkArgument(value >= 0, "The parameter 'value' has to be greater than zero");
        recurrentLinkProbability = value;
    }
    
    public double getSynapseLinkProbability() {
        return synapseLinkProbability;
    }
    
    public void setSynapseLinkProbability(double value) {
        Preconditions.checkArgument(value >= 0, "The parameter 'value' has to be greater than zero");
        synapseLinkProbability = value;
    }

    public Genome generate(ImageType imageType, Rng rng) {
        final HistoryList h = getHistoryList();
        final ActivationFunction outFunc = getOutputActivationFunction();
        final int numInputNodes = 2;
        final int numHiddenNodes = minHiddenNodes + rng.nextInt(maxHiddenNodes - minHiddenNodes + 1);
        final int numOutputNodes = imageType.getPatternGeneratorOutputSize();
        final int numNodes = numInputNodes + numHiddenNodes + numOutputNodes;
        final int numActivationFunctions = numHiddenNodes + (outFunc == null ? numOutputNodes : 0);
        final int[] innovations = new int[numNodes];
        final Genome result = new Genome();
        final ActivationFunction[] activationFunctions = provideActivationFunctions(numActivationFunctions, rng);
        int nextFunc = 0;
        for (int i = 0; i < numInputNodes; i++) {
            final int innovation = h.getInputNeuronInnovationNumber(i);
            innovations[i] = innovation;
            result.add(new Node(NodeType.Input, innovation));
        }
        for (int i = 0; i < numHiddenNodes; i++) {
            final int innovation = h.getHiddenNeuronInnovationNumber(i);
            innovations[numInputNodes + i] = innovation;
            result.add(new Node(NodeType.Hidden, innovation, activationFunctions[nextFunc++]));
        }
        for (int i = 0; i < numOutputNodes; i++) {
            final int innovation = h.getOutputNeuronInnovationNumber(i);
            innovations[numInputNodes + numHiddenNodes + i] = innovation;
            result.add(new Node(NodeType.Output, innovation, outFunc == null ? activationFunctions[nextFunc++] : outFunc));
        }
        for (int i = numInputNodes; i < numNodes; i++) {
            final int numLinks = minLinksPerNode + rng.nextInt(maxLinksPerNode - minLinksPerNode + 1);
            for (int j = 0; j < numLinks; j++) {
                final Node targetNode = result.getNodeByInnovation(innovations[i]);
                final int numSynapses = targetNode.getSynapseDefaults().length;
                final int targetSynapse = numSynapses > 1 && rng.nextDouble() < synapseLinkProbability ? 1 + rng.nextInt(numSynapses - 1) : 0;
                final int sourceNode = rng.nextDouble() < recurrentLinkProbability ? innovations[i + rng.nextInt(numNodes - i)] : innovations[rng.nextInt(i)];
                final LinkInnovation linkInnovation = h.getLinkInnovation(sourceNode, targetNode.getInnovationNumber(), targetSynapse);
                if (result.getLinkByInnovation(linkInnovation.getLinkInnovationNumber()) == null) {
                    final Link link = new Link(linkInnovation);
                    link.setWeight(rng.nextDouble() * 3 - 1.5);
                    result.add(link);
                }
            }
        }
        return result;
    }

}

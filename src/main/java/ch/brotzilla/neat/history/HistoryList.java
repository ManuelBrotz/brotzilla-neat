package ch.brotzilla.neat.history;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class HistoryList {
    
    public static final HashFunction hashFunction = Hashing.goodFastHash(32);
    
    private final int[] inputHistory, outputHistory;
    private final HashMap<LinkHistoryKey, LinkInnovation> linkHistory;
    private final Multimap<NodeHistoryKey, NodeInnovation> nodeHistory;
    
    private int nextInnovationNumber = 1;
    
    private int[] registerInnovationNumbers(int numbers) {
        Preconditions.checkArgument(numbers >= 0, "The parameter 'numbers' has to be greater than or equal to zero");
        if (numbers > 0) {
            final int[] result = new int[numbers];
            for (int i = 0; i < numbers; i++) {
                result[i] = newInnovationNumber();
            }
            return result;
        }
        return null;
    }
    
    public HistoryList() {
        this(0, 0);
    }
    
    public HistoryList(int inputNeurons, int outputNeurons) {
        Preconditions.checkArgument(inputNeurons >= 0, "The parameter 'inputNeurons' has to be greater than or equal to zero");
        Preconditions.checkArgument(outputNeurons >= 0, "The parameter 'outputNeurons' has to be greater than or equal to zero");
        inputHistory = registerInnovationNumbers(inputNeurons);
        outputHistory = registerInnovationNumbers(outputNeurons);
        linkHistory = Maps.newHashMap();
        nodeHistory = HashMultimap.create();
    }

    public int newInnovationNumber() {
        return nextInnovationNumber++;
    }
    
    public int getNumberOfInputNeurons() {
        return inputHistory == null ? 0 : inputHistory.length;
    }
    
    public int getNumberOfOutputNeurons() {
        return outputHistory == null ? 0 : outputHistory.length;
    }
    
    public int getInputNeuronInnovationNumber(int neuronIndex) {
        if (inputHistory == null) {
            throw new UnsupportedOperationException();
        }
        return inputHistory[neuronIndex];
    }
    
    public int getOutputNeuronInnovationNumber(int neuronIndex) {
        if (outputHistory == null) {
            throw new UnsupportedOperationException();
        }
        return outputHistory[neuronIndex];
    }
    
    public LinkInnovation getLinkInnovation(LinkHistoryKey key) {
        Preconditions.checkNotNull(key, "The parameter 'key' must not be null");
        return linkHistory.get(key);
    }
    
    public LinkInnovation newLinkInnovation(LinkHistoryKey key) {
        Preconditions.checkNotNull(key, "The parameter 'key' must not be null");
        Preconditions.checkArgument(!linkHistory.containsKey(key), "The parameter 'key' is already part of the link innovation history");
        final LinkInnovation result = new LinkInnovation(newInnovationNumber(), key);
        linkHistory.put(key, result);
        return result;
    }
    
    public Collection<NodeInnovation> getNodeInnovations(NodeHistoryKey key) {
        return Collections.unmodifiableCollection(nodeHistory.get(key));
    }
    
    public NodeInnovation newNodeInnovation(NodeHistoryKey key) {
        Preconditions.checkNotNull(key, "The parameter 'key' must not be null");
        final int nodeInnovationNumber = newInnovationNumber();
        final int sourceLinkInnovationNumber = newInnovationNumber();
        final int targetLinkInnovationNumber = newInnovationNumber();
        final LinkHistoryKey sourceLinkHistoryKey = new LinkHistoryKey(key.getSourceNode(), nodeInnovationNumber, 0);
        final LinkInnovation sourceLinkInnovation = new LinkInnovation(sourceLinkInnovationNumber, sourceLinkHistoryKey);
        final LinkHistoryKey targetLinkHistoryKey = new LinkHistoryKey(nodeInnovationNumber, key.getTargetNode(), key.getTargetSynapse());
        final LinkInnovation targetLinkInnovation = new LinkInnovation(targetLinkInnovationNumber, targetLinkHistoryKey);
        final NodeInnovation result = new NodeInnovation(nodeInnovationNumber, key, sourceLinkInnovation, targetLinkInnovation);
        Preconditions.checkState(!linkHistory.containsKey(sourceLinkHistoryKey), "Internal Error: The link innovation history already contains a link from " + key.getSourceNode() + " to " + nodeInnovationNumber);
        Preconditions.checkState(!linkHistory.containsKey(targetLinkHistoryKey), "Internal Error: The link innovation history already contians a link from " + nodeInnovationNumber + " to " + key.getTargetNode() + ":" + key.getTargetSynapse());
        nodeHistory.put(key, result);
        linkHistory.put(sourceLinkHistoryKey, sourceLinkInnovation);
        linkHistory.put(targetLinkHistoryKey, targetLinkInnovation);
        return result;
    }
    
}

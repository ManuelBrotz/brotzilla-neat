package ch.brotzilla.neat.history;

import gnu.trove.impl.Constants;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;

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
    
    private final TIntIntMap inputHistory, outputHistory;
    private final HashMap<LinkHistoryKey, LinkInnovation> linkHistory;
    private final Multimap<NodeHistoryKey, NodeInnovation> nodeHistory;
    
    private int nextInnovationNumber = 1;
    
    public HistoryList() {
        inputHistory = new TIntIntHashMap(Constants.DEFAULT_CAPACITY, Constants.DEFAULT_LOAD_FACTOR, -2, 0);
        outputHistory = new TIntIntHashMap(Constants.DEFAULT_CAPACITY, Constants.DEFAULT_LOAD_FACTOR, -1, 0);
        linkHistory = Maps.newHashMap();
        nodeHistory = HashMultimap.create();
    }
    
    public HistoryList(boolean biasNeuron, int inputNeurons, int outputNeurons) {
        this();
        Preconditions.checkArgument(inputNeurons >= 0, "The parameter 'inputNeurons' has to be greater than or equal to zero");
        Preconditions.checkArgument(outputNeurons >= 0, "The parameter 'outputNeurons' has to be greater than or equal to zero");
        if (biasNeuron) {
            inputHistory.put(-1, newInnovationNumber());
        }
        for (int i = 0; i < inputNeurons; i++) {
            inputHistory.put(i, newInnovationNumber());
        }
        for (int i = 0; i < outputNeurons; i++) {
            outputHistory.put(i, newInnovationNumber());
        }
    }

    public int newInnovationNumber() {
        return nextInnovationNumber++;
    }

    public int getBiasNeuronInnovationNumber() {
        return getInputNeuronInnovationNumber(-1);
    }
    
    public int getInputNeuronInnovationNumber(int neuronIndex) {
        Preconditions.checkArgument(neuronIndex >= -1, "The parameter 'neuronIndex' has to be greater than or equal to -1");
        int result = inputHistory.get(neuronIndex);
        if (result == inputHistory.getNoEntryValue()) {
            result = newInnovationNumber();
            inputHistory.put(neuronIndex, result);
        }
        return result;
    }

    public int getOutputNeuronInnovationNumber(int neuronIndex) {
        Preconditions.checkArgument(neuronIndex >= 0, "The parameter 'neuronIndex' has to be greater than or equal to zero");
        int result = outputHistory.get(neuronIndex);
        if (result == outputHistory.getNoEntryValue()) {
            result = newInnovationNumber();
            outputHistory.put(neuronIndex, result);
        }
        return result;
    }

    public LinkInnovation getLinkInnovation(LinkHistoryKey key) {
        Preconditions.checkNotNull(key, "The parameter 'key' must not be null");
        LinkInnovation result = linkHistory.get(key);
        if (result == null) {
            result = new LinkInnovation(newInnovationNumber(), key);
            linkHistory.put(key, result);
        }
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

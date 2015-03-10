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
    
    private final HashMap<LinkHistoryKey, LinkInnovation> linkHistory;
    private final Multimap<NodeHistoryKey, NodeInnovation> nodeHistory;
    
    private int nextInnovationNumber = 1;
    
    public HistoryList() {
        linkHistory = Maps.newHashMap();
        nodeHistory = HashMultimap.create();
    }

    public int newInnovationNumber() {
        return nextInnovationNumber++;
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

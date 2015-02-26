package ch.brotzilla.neat.neuralnet;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Genome {

    private final List<Node> nodes, nodesWrapper;
    private final List<Link> links, linksWrapper;
    private final HashMap<Integer, Node> nodesMap;
    private final HashMap<Integer, Link> linksMap;
    
    public Genome() {
        nodes = Lists.newArrayList();
        nodesWrapper = Collections.unmodifiableList(nodes);
        links = Lists.newArrayList();
        linksWrapper = Collections.unmodifiableList(links);
        nodesMap = Maps.newHashMap();
        linksMap = Maps.newHashMap();
    }

    public List<Node> getNodes() {
        return nodesWrapper;
    }
    
    public List<Link> getLinks() {
        return linksWrapper;
    }
    
    public Node getNodeByIndex(int index) {
        return nodes.get(index);
    }
    
    public Node getNodeByInnovation(int innovationNumber) {
        Preconditions.checkArgument(innovationNumber > 0, "The parameter 'innovationNumber' has to be greater than zero");
        return nodesMap.get(innovationNumber);
    }
    
    public Link getLinkByIndex(int index) {
        return links.get(index);
    }
    
    public Link getLinkByInnovation(int innovationNumber) {
        Preconditions.checkArgument(innovationNumber > 0, "The parameter 'innovationNumber' has to be greater than zero");
        return linksMap.get(innovationNumber);
    }
    
    public void add(Node node) {
        Preconditions.checkNotNull(node, "The parameter 'node' must not be null");
        Preconditions.checkArgument(!nodesMap.containsKey(node.getInnovationNumber()), "The genome already contains a node with the innovation number " + node.getInnovationNumber());
        nodes.add(node);
        nodesMap.put(node.getInnovationNumber(), node);
    }
    
    public void add(Link link) {
        Preconditions.checkNotNull(link, "The parameter 'link' must not be null");
        Preconditions.checkArgument(!linksMap.containsKey(link.getInnovationNumber()), "The genome already contains a link with the innovation number " + link.getInnovationNumber());
        final Node sourceNode = nodesMap.get(link.getSourceNode());
        Preconditions.checkArgument(sourceNode != null, "The genome does not contain a source node with the innovation number " + link.getSourceNode());
        final Node targetNode = nodesMap.get(link.getTargetNode());
        Preconditions.checkArgument(targetNode != null, "The genome does not contain a target node with the innovation number " + link.getTargetNode());
        Preconditions.checkArgument(targetNode.getType().isTargetNode(), "The node with the innovation number " + targetNode.getInnovationNumber() + " has to be of type Output or Hidden");
        links.add(link);
        linksMap.put(link.getInnovationNumber(), link);
    }
    
    public void remove(Node node) {
        Preconditions.checkNotNull(node, "The parameter 'node' must not be null");
        final boolean removed = nodes.remove(node);
        Preconditions.checkArgument(removed, "The parameter 'node' is not part of this genome");
        nodesMap.remove(node.getInnovationNumber());
    }
    
    public void remove(Link link) {
        Preconditions.checkNotNull(link, "The parameter 'link' must not be null");
        final boolean removed = links.remove(link);
        Preconditions.checkArgument(removed, "The parameter 'link' is not part of this genome");
        linksMap.remove(link.getInnovationNumber());
    }
    
    public void removeNodeByIndex(int index) {
        final Node node = nodes.get(index);
        nodes.remove(index);
        nodesMap.remove(node.getInnovationNumber());
    }
    
    public void removeNodeByInnovation(int innovationNumber) {
        Preconditions.checkArgument(innovationNumber > 0, "The parameter 'innovationNumber' has to be greater than zero");
        final Node node = nodesMap.remove(innovationNumber);
        Preconditions.checkArgument(node != null, "The genome does not contain a node with the innovation number " + innovationNumber);
        nodes.remove(node);
    }
    
    public void removeLinkByIndex(int index) {
        final Link link = links.get(index);
        links.remove(index);
        linksMap.remove(link.getInnovationNumber());
    }
    
    public void removeLinkByInnovation(int innovationNumber) {
        Preconditions.checkArgument(innovationNumber > 0, "The parameter 'innovationNumber' has to be greater than zero");
        final Link link = linksMap.remove(innovationNumber);
        Preconditions.checkArgument(link != null, "The genome does not contain a link with the innovation number " + innovationNumber);
        links.remove(link);
    }
    
}

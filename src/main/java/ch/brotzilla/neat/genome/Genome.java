package ch.brotzilla.neat.genome;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Genome {

    private Node biasNode;
    private final List<Node> inputNodes, hiddenNodes, outputNodes, inputWrapper, hiddenWrapper, outputWrapper;
    private final List<Link> links, linksWrapper;
    private final HashMap<Integer, Node> nodesMap;
    private final HashMap<Integer, Link> linksMap;
    
    private int inputSize, hiddenSize, totalSize;
    
    private Node removeNodeFromLists(int index) {
        Preconditions.checkElementIndex(index, totalSize, "The parameter 'index'");
        if (biasNode != null) {
            if (index == 0) {
                final Node result = biasNode;
                biasNode = null;
                return result;
            }
            --index;
        }
        if (index < inputSize) {
            return inputNodes.remove(index);
        }
        index -= inputSize;
        if (index < hiddenSize) {
            return hiddenNodes.remove(index);
        }
        index -= hiddenSize;
        return outputNodes.remove(index);
    }
    
    private void decrementSizes(NodeType type) {
        if (type == NodeType.Input) {
            --inputSize;
        } else if (type == NodeType.Hidden) {
            --hiddenSize;
        }
        --totalSize;
    }
    
    private void incrementSizes(NodeType type) {
        if (type == NodeType.Input) {
            ++inputSize;
        } else if (type == NodeType.Hidden) {
            ++hiddenSize;
        }
        ++totalSize;
    }
    
    public Genome() {
        inputNodes = Lists.newArrayList();
        hiddenNodes = Lists.newArrayList();
        outputNodes = Lists.newArrayList();
        inputWrapper = Collections.unmodifiableList(inputNodes);
        hiddenWrapper = Collections.unmodifiableList(hiddenNodes);
        outputWrapper = Collections.unmodifiableList(outputNodes);
        links = Lists.newArrayList();
        linksWrapper = Collections.unmodifiableList(links);
        nodesMap = Maps.newHashMap();
        linksMap = Maps.newHashMap();
    }

    public final int getNumberOfNodes() {
        return totalSize;
    }
    
    public final Node getBiasNode() {
        return biasNode;
    }
    
    public final List<Node> getInputNodes() {
        return inputWrapper;
    }
    
    public final List<Node> getHiddenNodes() {
        return hiddenWrapper;
    }
    
    public final List<Node> getOutputNodes() {
        return outputWrapper;
    }
    
    public final List<Link> getLinks() {
        return linksWrapper;
    }
    
    public final Node getNodeByIndex(int index) {
        Preconditions.checkElementIndex(index, totalSize, "The parameter 'index'");
        if (biasNode != null) {
            if (index == 0) {
                return biasNode;
            }
            --index;
        }
        if (index < inputSize) {
            return inputNodes.get(index);
        }
        index -= inputSize;
        if (index < hiddenSize) {
            return hiddenNodes.get(index);
        }
        index -= hiddenSize;
        return outputNodes.get(index);
    }
    
    public final Node getNodeByInnovation(int innovationNumber) {
        Preconditions.checkArgument(innovationNumber > 0, "The parameter 'innovationNumber' has to be greater than zero");
        return nodesMap.get(innovationNumber);
    }
    
    public final Link getLinkByIndex(int index) {
        return links.get(index);
    }
    
    public final Link getLinkByInnovation(int innovationNumber) {
        Preconditions.checkArgument(innovationNumber > 0, "The parameter 'innovationNumber' has to be greater than zero");
        return linksMap.get(innovationNumber);
    }
    
    public final void add(Node node) {
        Preconditions.checkNotNull(node, "The parameter 'node' must not be null");
        Preconditions.checkArgument(!nodesMap.containsKey(node.getInnovationNumber()), "The genome already contains a node with the innovation number " + node.getInnovationNumber());
        if (node.getType() == NodeType.Bias) {
            Preconditions.checkArgument(biasNode == null, "The genome already contains a bias node");
            biasNode = node;
        } else {
            node.getType().selectNodesList(inputNodes, hiddenNodes, outputNodes).add(node);
        }
        nodesMap.put(node.getInnovationNumber(), node);
        incrementSizes(node.getType());
    }
    
    public final void add(Link link) {
        Preconditions.checkNotNull(link, "The parameter 'link' must not be null");
        Preconditions.checkArgument(!linksMap.containsKey(link.getInnovationNumber()), "The genome already contains a link with the innovation number " + link.getInnovationNumber());
        links.add(link);
        linksMap.put(link.getInnovationNumber(), link);
    }
    
    public final void remove(Node node) {
        Preconditions.checkNotNull(node, "The parameter 'node' must not be null");
        if (node.getType() == NodeType.Bias) {
            Preconditions.checkArgument(node == biasNode, "The parameter 'node' is not part of this genome");
            biasNode = null;
        } else {
            Preconditions.checkArgument(node.getType().selectNodesList(inputNodes, hiddenNodes, outputNodes).remove(node), "The parameter 'node' is not part of this genome");
        }
        nodesMap.remove(node.getInnovationNumber());
        decrementSizes(node.getType());
    }
    
    public final void removeNodeByIndex(int index) {
        Preconditions.checkElementIndex(index, totalSize, "The parameter 'index'");
        final Node node = removeNodeFromLists(index);
        nodesMap.remove(node.getInnovationNumber());
        decrementSizes(node.getType());
    }
    
    public final void removeNodeByInnovation(int innovationNumber) {
        Preconditions.checkArgument(innovationNumber > 0, "The parameter 'innovationNumber' has to be greater than zero");
        final Node node = nodesMap.remove(innovationNumber);
        Preconditions.checkArgument(node != null, "The genome does not contain a node with the innovation number " + innovationNumber);
        if (node == biasNode) {
            biasNode = null;
        } else {
            node.getType().selectNodesList(inputNodes, hiddenNodes, outputNodes).remove(node);
        }
        decrementSizes(node.getType());
    }
    
    public final void remove(Link link) {
        Preconditions.checkNotNull(link, "The parameter 'link' must not be null");
        Preconditions.checkArgument(links.remove(link), "The parameter 'link' is not part of this genome");
        linksMap.remove(link.getInnovationNumber());
    }
    
    public final void removeLinkByIndex(int index) {
        final Link link = links.get(index);
        links.remove(index);
        linksMap.remove(link.getInnovationNumber());
    }
    
    public final void removeLinkByInnovation(int innovationNumber) {
        Preconditions.checkArgument(innovationNumber > 0, "The parameter 'innovationNumber' has to be greater than zero");
        final Link link = linksMap.remove(innovationNumber);
        Preconditions.checkArgument(link != null, "The genome does not contain a link with the innovation number " + innovationNumber);
        links.remove(link);
    }
    
}

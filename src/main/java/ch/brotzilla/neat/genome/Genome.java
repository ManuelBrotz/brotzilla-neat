package ch.brotzilla.neat.genome;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import ch.brotzilla.neat.Debug;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class Genome implements Iterable<Node> {

    private Node biasNode;
    private final List<Node> inputNodes, hiddenNodes, outputNodes, inputWrapper, hiddenWrapper, outputWrapper;
    private final List<Link> links, linksWrapper;
    private final TIntObjectMap<Node> nodesMap;
    private final TIntObjectMap<Link> linksMap;
    
    private int inputSize, hiddenSize, outputSize, totalSize;
    
    private void add(Link link, boolean updateTargetNode) {
        Preconditions.checkNotNull(link, "The parameter 'link' must not be null");
        Preconditions.checkArgument(!linksMap.containsKey(link.getInnovationNumber()), "The genome already contains a link with the innovation number " + link.getInnovationNumber());
        if (Debug.EnableIntegrityChecks) {
            final Node sourceNode = nodesMap.get(link.getSourceNode());
            Preconditions.checkArgument(sourceNode != null, "The genome does not contain a source node with the innovation number " + link.getSourceNode());
            final Node targetNode = nodesMap.get(link.getTargetNode());
            Preconditions.checkArgument(targetNode != null, "The genome does not contain a target node with the innovation number " + link.getTargetNode());
            Preconditions.checkArgument(targetNode.getType().isTargetNode(), "The node with the innovation number " + targetNode.getInnovationNumber() + " has to be of type Hidden or Output");
        }
        if (updateTargetNode) {
            final Node targetNode = nodesMap.get(link.getTargetNode());
            Preconditions.checkArgument(targetNode != null, "The genome does not contain a target node with the innovation number " + link.getTargetNode());
            targetNode.add(link);
        }
        links.add(link);
        linksMap.put(link.getInnovationNumber(), link);
    }
    
    private void removeLinkFromNode(Link link) {
        Preconditions.checkNotNull(link, "The parameter 'link' must not be null");
        final Node targetNode = nodesMap.get(link.getTargetNode());
        Preconditions.checkArgument(targetNode != null, "The genome does not contain a target node with the innovation number " + link.getTargetNode());
        targetNode.remove(link);
    }

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
        } else if (type == NodeType.Output) {
        	--outputSize;
        }
        --totalSize;
    }
    
    private void incrementSizes(NodeType type) {
        if (type == NodeType.Input) {
            ++inputSize;
        } else if (type == NodeType.Hidden) {
            ++hiddenSize;
        } else if (type == NodeType.Output) {
        	++outputSize;
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
        nodesMap = new TIntObjectHashMap<Node>();
        linksMap = new TIntObjectHashMap<Link>();
    }
    
    public Genome(Genome source) {
        this();
        Preconditions.checkNotNull(source, "The parameter 'source' must not be null");
        for (final Node node : source) {
            add(node.clone());
        }
        for (final Link link : source.links) {
            add(link.clone(), false);
        }
        inputSize = source.inputSize;
        hiddenSize = source.hiddenSize;
        totalSize = source.totalSize;
    }

    public final int getNumberOfNodes() {
        return totalSize;
    }
    
    public final int getNumberOfInputNodes() {
    	return inputSize;
    }
    
    public final int getNumberOfHiddenNodes() {
    	return hiddenSize;
    }
    
    public final int getNumberOfOutputNodes() {
    	return outputSize;
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
    
    public Iterator<Node> iterator() {
        return new NodesIterator();
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
        add(link, true);
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
        removeLinkFromNode(link);
    }
    
    public final void removeLinkByIndex(int index) {
        final Link link = links.remove(index);
        linksMap.remove(link.getInnovationNumber());
        removeLinkFromNode(link);
    }
    
    public final void removeLinkByInnovation(int innovationNumber) {
        Preconditions.checkArgument(innovationNumber > 0, "The parameter 'innovationNumber' has to be greater than zero");
        final Link link = linksMap.remove(innovationNumber);
        Preconditions.checkArgument(link != null, "The genome does not contain a link with the innovation number " + innovationNumber);
        links.remove(link);
        removeLinkFromNode(link);
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Genome) {
            final Genome genome = (Genome) other;
            if (inputNodes.size() != genome.inputNodes.size() || hiddenNodes.size() != genome.hiddenNodes.size() || outputNodes.size() != genome.outputNodes.size()) {
                return false;
            }
            if (links.size() != genome.links.size()) {
                return false;
            }
            if (!(biasNode == null ? genome.biasNode == null : biasNode.equals(genome.biasNode))) {
                return false;
            }
            if (!inputNodes.equals(genome.inputNodes)) {
                return false;
            }
            if (!hiddenNodes.equals(genome.hiddenNodes)) {
                return false;
            }
            if (!outputNodes.equals(genome.outputNodes)) {
                return false;
            }
            if (!links.equals(genome.links)) {
                return false;
            }
            return true;
        }
        return false;
    }
    
    @Override
    public Genome clone() {
        return new Genome(this);
    }
    
    private class NodesIterator implements Iterator<Node> {

        private final int expectedSize;
        private final Iterator<Node>[] iterators;
        private Iterator<Node> iterator;
        private int nextIterator;
        
        private Iterator<Node>[] initIterators() {
            final int numIterators = (biasNode != null ? 1 : 0) + (inputSize > 0 ? 1 : 0) + (hiddenSize > 0 ? 1 : 0) + (outputNodes.size() > 0 ? 1 : 0);
            if (numIterators > 0) {
                @SuppressWarnings("unchecked")
                final Iterator<Node>[] result = new Iterator[numIterators];
                int index = 0;
                if (biasNode != null) {
                    result[index++] = new BiasNodeIterator();
                }
                if (inputSize > 0) {
                    result[index++] = inputNodes.iterator();
                }
                if (hiddenSize > 0) {
                    result[index++] = hiddenNodes.iterator();
                }
                if (outputNodes.size() > 0) {
                    result[index] = outputNodes.iterator();
                }
                return result;
            }
            return null;
        }
        
        public NodesIterator() {
            expectedSize = totalSize;
            iterators = initIterators();
            iterator = (iterators == null ? null : iterators[0]);
            nextIterator = 1;
        }

        public boolean hasNext() {
            return iterator != null && iterator.hasNext();
        }

        public Node next() {
            if (totalSize != expectedSize) {
                throw new ConcurrentModificationException();
            }
            if (iterator == null) {
                throw new NoSuchElementException();
            }
            final Node node = iterator.next();
            if (!iterator.hasNext()) {
                if (nextIterator < iterators.length) {
                    iterator = iterators[nextIterator++];
                } else {
                    iterator = null;
                }
            }
            return node;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
    
    private class BiasNodeIterator implements Iterator<Node> {

        private boolean hasNext = true;
        
        public BiasNodeIterator() {}
        
        public boolean hasNext() {
            return hasNext;
        }

        public Node next() {
            hasNext = false;
            return biasNode;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
        
    }

}

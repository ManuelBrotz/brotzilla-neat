package ch.brotzilla.neat.genome;

import gnu.trove.iterator.TIntIterator;
import ch.brotzilla.neat.history.HistoryList;
import ch.brotzilla.neat.history.LinkHistoryKey;
import ch.brotzilla.neat.history.LinkInnovation;
import ch.brotzilla.neat.history.NodeHistoryKey;
import ch.brotzilla.neat.history.NodeInnovation;
import ch.brotzilla.neat.math.ActivationFunction;
import ch.brotzilla.neat.math.ExtendedPeriodicCosFunction;

public class Test {

    public static final ActivationFunction testFunction = new ExtendedPeriodicCosFunction();
    
    public static final HistoryList history = new HistoryList();

    public Test() {}

    public static Genome testCreateGenome(int inputNodes, int outputNodes) {
        final Genome genome = new Genome();
        
        System.out.println("Create genome with " + inputNodes + " input nodes and " + outputNodes + " output nodes.");
        
        // add input nodes
        for (int i = 0; i < inputNodes; i++) {
            final Node node = new Node(NodeType.Input, history.getInputNeuronInnovationNumber(i));
            genome.add(node);
            System.out.println("New Input-Node: " + node.getInnovationNumber());
        }
        
        // add output nodes
        for (int i = 0; i < outputNodes; i++) {
            final Node node = new Node(NodeType.Output, history.getOutputNeuronInnovationNumber(i), testFunction);
            genome.add(node);
            System.out.println("New Output-Node: " + node.getInnovationNumber());
        }
        
        // add links
        for (int i = 0; i < inputNodes; i++) {
            for (int j = 0; j < outputNodes; j++) {
                final Node sourceNode = genome.getInputNodes().get(i);
                final Node targetNode = genome.getOutputNodes().get(j);
                final LinkHistoryKey key = new LinkHistoryKey(sourceNode.getInnovationNumber(), targetNode.getInnovationNumber(), 0);
                final LinkInnovation innovation = history.getLinkInnovation(key);
                final Link link = new Link(innovation.getLinkInnovationNumber(), sourceNode.getInnovationNumber(), targetNode.getInnovationNumber(), 0);
                genome.add(link);
                System.out.println("New Link from Input" + sourceNode.getInnovationNumber() + " to Output" + targetNode.getInnovationNumber() + ": " + link.getInnovationNumber());
            }
        }
        
        System.out.println();
        
        return genome;
    }
    
    public static Genome testAddHiddenNodes(Genome genome) {
        
        final int inputNodes = genome.getInputNodes().size();
        final int outputNodes = genome.getOutputNodes().size();

        System.out.println("Add " + (inputNodes * outputNodes) + " hidden nodes.");

        for (int i = 0; i < inputNodes; i++) {
            for (int j = 0; j < outputNodes; j++) {
                final Node sourceNode = genome.getInputNodes().get(i);
                final Node targetNode = genome.getOutputNodes().get(j);
                final NodeHistoryKey nodeHistoryKey = new NodeHistoryKey(sourceNode.getInnovationNumber(), targetNode.getInnovationNumber(), 0, testFunction.getID());
                final NodeInnovation nodeInnovation = history.newNodeInnovation(nodeHistoryKey);
                genome.add(new Node(NodeType.Hidden, nodeInnovation.getNodeInnovationNumber(), testFunction));
                genome.add(new Link(nodeInnovation.getSourceLinkInnovation()));
                genome.add(new Link(nodeInnovation.getTargetLinkInnovation()));
                System.out.println("New Hidden-Node " + nodeInnovation.getNodeInnovationNumber() +" from Input" + sourceNode.getInnovationNumber() + " to Output" + targetNode.getInnovationNumber() + ": L" + nodeInnovation.getSourceLinkInnovationNumber() + ", L" + nodeInnovation.getTargetLinkInnovationNumber()); 
            }
        }
        
        System.out.println();
        
        return genome;
    }
    
    public static Genome testAddBiasNode(Genome genome) {
        System.out.println("Add bias node:");
        
        final Node biasNode = new Node(NodeType.Bias, history.newInnovationNumber());
        genome.add(biasNode);
        
        System.out.println("New Bias-Node: " + biasNode.getInnovationNumber());
        System.out.println();
        
        System.out.println("Add new links from bias node to hidden nodes:");
        for (final Node targetNode : genome.getHiddenNodes()) {
            final LinkHistoryKey key = new LinkHistoryKey(biasNode.getInnovationNumber(), targetNode.getInnovationNumber(), 0);
            final LinkInnovation innovation = history.getLinkInnovation(key);
            genome.add(new Link(innovation));
            System.out.println("New Link " + innovation.getLinkInnovationNumber() + " from Bias-Node to " + targetNode.getType() + "" + targetNode.getInnovationNumber());
        }
        System.out.println();
        
        System.out.println("Add new links from bias node to output nodes:");
        for (final Node targetNode : genome.getOutputNodes()) {
            final LinkHistoryKey key = new LinkHistoryKey(biasNode.getInnovationNumber(), targetNode.getInnovationNumber(), 0);
            final LinkInnovation innovation = history.getLinkInnovation(key);
            genome.add(new Link(innovation));
            System.out.println("New Link " + innovation.getLinkInnovationNumber() + " from Bias-Node to " + targetNode.getType() + "" + targetNode.getInnovationNumber());
        }
        System.out.println();
        
        return genome;
    }
    
    public static Genome testNodeIterationAndIndexedAccess(Genome genome) {
        System.out.println("Node iteration and indexed access comparison:");
        int index = 0;
        for (final Node iteratedNode : genome) {
            final Node indexedNode = genome.getNodeByIndex(index++);
            System.out.println("Index " + (index - 1) + ": " + (iteratedNode == indexedNode ? "OK!" : "Wrong! (Iterated = " + iteratedNode.getType() + "" + iteratedNode.getInnovationNumber() + ", Indexed = " + indexedNode.getType() + "" + indexedNode.getInnovationNumber() + ")"));
        }
        System.out.println();
        return genome;
    }
    
    private static String getLinkList(Node node) {
        if (node.getNumberOfLinks() == 0) {
            return "";
        }
        final StringBuilder result = new StringBuilder();
        final TIntIterator it = node.getLinks().iterator();
        while (it.hasNext()) {
            final int link = it.next();
            if (result.length() > 0) {
                result.append(", ");
            }
            result.append(link);
        }
        return ", Links: " + result;
    }
    
    public static Genome printGenome(Genome genome) {
        System.out.println("All Nodes:");
        
        for (final Node node : genome) {
            System.out.println(node.getType() + "-Node: " + node.getInnovationNumber() + getLinkList(node));
        }
        
        System.out.println();
        System.out.println("All Links:");
        
        for (final Link link : genome.getLinks()) {
            final Node sourceNode = genome.getNodeByInnovation(link.getSourceNode());
            final Node targetNode = genome.getNodeByInnovation(link.getTargetNode());
            System.out.println("Link " + link.getInnovationNumber() + " from " + sourceNode.getType() + "" + link.getSourceNode() + " to " + targetNode.getType() + "" + link.getTargetNode());
        }
        
        System.out.println();
        
        return genome;
    }
    
    public static Genome testEquals(Genome genome) {
        final Genome clone = genome.clone();
        
        System.out.println("Equals Test:");
        System.out.println("Original equals clone: " + genome.equals(clone));
        
//        clone.add(new Link(history.newInnovationNumber(), clone.getInputNodes().get(0).getInnovationNumber(), clone.getHiddenNodes().get(0).getInnovationNumber(), 0));
        clone.getLinkByIndex(0).setWeight(10);
        System.out.println("Original equals modified clone: " + genome.equals(clone));
        
        System.out.println();
        
        return genome;
    }
    
    public static void main(String[] args) {
        testEquals(testNodeIterationAndIndexedAccess(printGenome(testAddBiasNode(testAddHiddenNodes(testCreateGenome(2, 3))))));
    }

}

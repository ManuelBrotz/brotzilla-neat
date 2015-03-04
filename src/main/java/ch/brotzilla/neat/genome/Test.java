package ch.brotzilla.neat.genome;

import ch.brotzilla.neat.history.HistoryList;
import ch.brotzilla.neat.history.LinkHistoryKey;
import ch.brotzilla.neat.history.LinkInnovation;
import ch.brotzilla.neat.history.NodeHistoryKey;
import ch.brotzilla.neat.history.NodeInnovation;
import ch.brotzilla.neat.math.ActivationFunction;
import ch.brotzilla.neat.math.CosFunction;

public class Test {

    public static final ActivationFunction testFunction = new CosFunction();
    
    public static final HistoryList history = new HistoryList();

    public Test() {}

    public static Genome testCreateGenome(int inputNodes, int outputNodes) {
        final Genome genome = new Genome();
        
        System.out.println("Create genome with " + inputNodes + " input nodes and " + outputNodes + " output nodes.");
        
        // add input nodes
        for (int i = 0; i < inputNodes; i++) {
            final Node node = new Node(NodeType.Input, history.newInnovationNumber());
            genome.add(node);
            System.out.println("New Input-Node: " + node.getInnovationNumber());
        }
        
        // add output nodes
        for (int i = 0; i < outputNodes; i++) {
            final Node node = new Node(NodeType.Output, history.newInnovationNumber(), testFunction);
            genome.add(node);
            System.out.println("New Output-Node: " + node.getInnovationNumber());
        }
        
        // add links
        for (int i = 0; i < inputNodes; i++) {
            for (int j = 0; j < outputNodes; j++) {
                final Node sourceNode = genome.getInputNodes().get(i);
                final Node targetNode = genome.getOutputNodes().get(j);
                final LinkHistoryKey key = new LinkHistoryKey(sourceNode.getInnovationNumber(), targetNode.getInnovationNumber(), 0);
                final LinkInnovation innovation = history.newLinkInnovation(key);
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
                genome.add(new Link(nodeInnovation.getSourceLinkInnovationNumber(), sourceNode.getInnovationNumber(), nodeInnovation.getNodeInnovationNumber(), 0));
                genome.add(new Link(nodeInnovation.getTargetLinkInnovationNumber(), nodeInnovation.getNodeInnovationNumber(), targetNode.getInnovationNumber(), 0));
                System.out.println("New Hidden-Node from Input" + sourceNode.getInnovationNumber() + " to Output" + targetNode.getInnovationNumber() + ": L" + nodeInnovation.getSourceLinkInnovationNumber() + ", N" + nodeInnovation.getNodeInnovationNumber() + ", L" + nodeInnovation.getTargetLinkInnovationNumber()); 
            }
        }
        
        System.out.println();
        
        return genome;
    }
    
    public static Genome printGenome(Genome genome) {
        System.out.println("All Nodes:");
        
        for (int i = 0; i < genome.getNumberOfNodes(); i++) {
            final Node node = genome.getNodeByIndex(i);
            System.out.println(node.getType() + "-Node: " + node.getInnovationNumber());
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
        testEquals(printGenome(testAddHiddenNodes(testCreateGenome(2, 2))));
    }

}

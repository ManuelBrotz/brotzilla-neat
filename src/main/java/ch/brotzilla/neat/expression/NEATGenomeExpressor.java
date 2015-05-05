package ch.brotzilla.neat.expression;

import java.util.List;

import gnu.trove.iterator.TIntIterator;

import com.google.common.base.Preconditions;

import ch.brotzilla.neat.genome.Genome;
import ch.brotzilla.neat.genome.Link;
import ch.brotzilla.neat.genome.Node;
import ch.brotzilla.neat.genome.NodeType;
import ch.brotzilla.neat.genome.index.LayeredIndexStrategy;
import ch.brotzilla.neat.genome.index.NodeIndex;
import ch.brotzilla.neat.neuralnet.BiasConnection;
import ch.brotzilla.neat.neuralnet.Connection;
import ch.brotzilla.neat.neuralnet.HiddenNeuron;
import ch.brotzilla.neat.neuralnet.HiddenNeuronConnection;
import ch.brotzilla.neat.neuralnet.InputNeuronConnection;
import ch.brotzilla.neat.neuralnet.NeuralNet;
import ch.brotzilla.neat.neuralnet.Neuron;
import ch.brotzilla.neat.neuralnet.OutputNeuron;
import ch.brotzilla.neat.neuralnet.OutputNeuronConnection;

public class NEATGenomeExpressor implements GenomeExpressor {

    private Connection expressConnection(Genome genome, NodeIndex index, int biasInnovation, Link link) {
        final int sourceNodeInnovation = link.getSourceNode();
        if (sourceNodeInnovation == biasInnovation) {
            return new BiasConnection(link.getTargetSynapse(), link.getWeight());
        } else {
            final Node sourceNode = genome.getNodeByInnovation(sourceNodeInnovation);
            Preconditions.checkArgument(sourceNode != null, "The parameter 'genome' does not contain a node with the innovation number " + sourceNodeInnovation);
            Preconditions.checkArgument(sourceNode.getType() != NodeType.Bias, "The parameter 'genome' must not contain more than one bias node");
            final int sourceNeuronIndex = index.getNodeIndex(sourceNodeInnovation);
            Preconditions.checkState(sourceNeuronIndex >= 0, "Internal Error: The node index does not contain a node with the innovation number " + sourceNodeInnovation);
            if (sourceNode.getType() == NodeType.Input) {
                return new InputNeuronConnection(sourceNeuronIndex, link.getTargetSynapse(), link.getWeight());
            } else if (sourceNode.getType() == NodeType.Hidden) {
                return new HiddenNeuronConnection(sourceNeuronIndex, link.getTargetSynapse(), link.getWeight());
            } else if (sourceNode.getType() == NodeType.Output) {
                return new OutputNeuronConnection(sourceNeuronIndex, link.getTargetSynapse(), link.getWeight());
            } else {
                throw new IllegalStateException("Unknown node type: " + sourceNode.getType());
            }
        }
    }
    
    private Neuron expressNeuron(Genome genome, NodeIndex index, int biasInnovation, Node node) {
        final int targetNeuronIndex = index.getNodeIndex(node.getInnovationNumber());
        Preconditions.checkState(targetNeuronIndex >= 0, "Internal Error: The node index does not contain a node with the innovation number " + node.getInnovationNumber());
        final Connection[] connections = new Connection[node.getNumberOfLinks()];
        final TIntIterator links = node.getLinks().iterator();
        int ci = 0;
        while (links.hasNext()) {
            final int linkInnovation = links.next();
            final Link link = genome.getLinkByInnovation(linkInnovation);
            Preconditions.checkArgument(link != null, "The parameter 'genome' does not contain a link with the innovation number " + linkInnovation);
            connections[ci++] = expressConnection(genome, index, biasInnovation, link);
        }
        if (node.getType() == NodeType.Hidden) {
            return new HiddenNeuron(targetNeuronIndex, node.getActivationFunction(), node.copySynapseDefaults(), connections);
        } else if (node.getType() == NodeType.Output) {
            return new OutputNeuron(targetNeuronIndex, node.getActivationFunction(), node.copySynapseDefaults(), connections);
        }
        throw new IllegalArgumentException("The parameter 'node' has to be of type Hidden or Output");
    }
    
    private Neuron[] expressNeurons(Genome genome, NodeIndex index, int biasInnovation, List<Node> nodes) {
        if (nodes.size() > 0) {
            final Neuron[] result = new Neuron[nodes.size()];
            int ni = 0;
            for (final Node node : nodes) {
                result[ni++] = expressNeuron(genome, index, biasInnovation, node);
            }
            return result;
        }
        return null;
    }
    
	public NEATGenomeExpressor() {}

	public NeuralNet express(Genome genome) {
		Preconditions.checkNotNull(genome, "The parameter 'genome' must not be null");
		Preconditions.checkArgument(genome.getNumberOfInputNodes() > 0, "The parameter 'genome' requires at least one input node");
		Preconditions.checkArgument(genome.getNumberOfOutputNodes() > 0, "The parameter 'genome' requires at least one output node");
        final int biasInnovation = (genome.getBiasNode() != null ? genome.getBiasNode().getInnovationNumber() : -1);
		final NodeIndex index = new NodeIndex(genome, new LayeredIndexStrategy());
		final Neuron[] hiddenNeurons = expressNeurons(genome, index, biasInnovation, genome.getHiddenNodes());
		final Neuron[] outputNeurons = expressNeurons(genome, index, biasInnovation, genome.getOutputNodes());
		return new NeuralNet(genome.getNumberOfInputNodes(), genome.getNumberOfHiddenNodes(), genome.getNumberOfOutputNodes(), hiddenNeurons, outputNeurons);
	}

}


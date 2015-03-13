package ch.brotzilla.neat.expression;

import com.google.common.base.Preconditions;

import ch.brotzilla.neat.genome.Genome;
import ch.brotzilla.neat.genome.Node;
import ch.brotzilla.neat.neuralnet.NeuralNet;
import ch.brotzilla.neat.neuralnet.Neuron;

public class DefaultGenomeExpressor implements GenomeExpressor {

	public DefaultGenomeExpressor() {

	}

	public NeuralNet express(Genome genome) {
		Preconditions.checkNotNull(genome, "The parameter 'genome' must not be null");
		Preconditions.checkArgument(genome.getNumberOfInputNodes() > 0, "The parameter 'genome' requires at least one input node");
		Preconditions.checkArgument(genome.getNumberOfOutputNodes() > 0, "The parameter 'genome' requires at least one output node");
		final NodeIndex index = new NodeIndex();
		index.createIndex(genome);
		final int biasInnovation = (genome.getBiasNode() != null ? genome.getBiasNode().getInnovationNumber() : -1);
		final Neuron[] hiddenNeurons = genome.getNumberOfInputNodes() == 0 ? null : new Neuron[genome.getNumberOfHiddenNodes()];
		final Neuron[] outputNeurons = new Neuron[genome.getNumberOfOutputNodes()];
		for (final Node node : genome.getHiddenNodes()) {
			
		}
	}

}


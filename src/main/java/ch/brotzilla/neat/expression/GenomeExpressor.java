package ch.brotzilla.neat.expression;

import ch.brotzilla.neat.genome.Genome;
import ch.brotzilla.neat.neuralnet.NeuralNet;

public interface GenomeExpressor {

	NeuralNet express(Genome genome);
	
}

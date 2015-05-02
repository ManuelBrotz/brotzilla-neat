package ch.brotzilla.neat.genome;

import com.google.common.base.Preconditions;

import ch.brotzilla.neat.evolution.Rng;
import ch.brotzilla.neat.history.HistoryList;
import ch.brotzilla.neat.math.ActivationFunction;

// TODO implement special RNG class for handling weights

public final class Genomes {

    private Genomes() {}
    
    public static Genome createMinimalGenome(boolean biasNeuron, int inputNeurons, int outputNeurons, ActivationFunction outputActivationFunction, Rng rng, HistoryList historyList) {
        Preconditions.checkArgument(inputNeurons > 0, "The parameter 'inputNeurons' has to be greater than zero");
        Preconditions.checkArgument(outputNeurons > 0, "The parameter 'outputNeurons' has to be greater than zero");
        Preconditions.checkNotNull(outputActivationFunction, "The parameter 'outputActivationFunction' must not be null");
        Preconditions.checkNotNull(rng, "The parameter 'rng' must not be null");
        Preconditions.checkNotNull(historyList, "The parameter 'historyList' must not be null");
        final Genome genome = new Genome();
        if (biasNeuron) {
            final Node node = new Node(NodeType.Bias, historyList.getBiasNeuronInnovationNumber());
            genome.add(node);
        }
        for (int i = 0; i < inputNeurons; i++) {
            final Node node = new Node(NodeType.Input, historyList.getInputNeuronInnovationNumber(i));
            genome.add(node);
        }
        for (int i = 0; i < outputNeurons; i++) {
            final Node node = new Node(NodeType.Output, historyList.getOutputNeuronInnovationNumber(i), outputActivationFunction);
            genome.add(node);
        }
        for (int o = 0; o < outputNeurons; o++) {
            if (biasNeuron) {
                final Link link = new Link(historyList.getInputOutputLinkInnovation(-1, o, 0));
                link.setWeight(rng.nextDouble() * 3.0 - 1.5);
                genome.add(link);
            }
            for (int i = 0; i < inputNeurons; i++) {
                final Link link = new Link(historyList.getInputOutputLinkInnovation(i, o, 0));
                link.setWeight(rng.nextDouble() * 3.0 - 1.5);
                genome.add(link);
            }
        }
        return genome;
    }

    public static Genome createFeatureSelectiveGenome(boolean biasNeuron, int inputNeurons, int outputNeurons, ActivationFunction outputActivationFunction, Rng rng, HistoryList historyList) {
        Preconditions.checkArgument(inputNeurons > 0, "The parameter 'inputNeurons' has to be greater than zero");
        Preconditions.checkArgument(outputNeurons > 0, "The parameter 'outputNeurons' has to be greater than zero");
        Preconditions.checkNotNull(outputActivationFunction, "The parameter 'outputActivationFunction' must not be null");
        Preconditions.checkNotNull(rng, "The parameter 'rng' must not be null");
        Preconditions.checkNotNull(historyList, "The parameter 'historyList' must not be null");
        final Genome genome = new Genome();
        if (biasNeuron) {
            final Node node = new Node(NodeType.Bias, historyList.getBiasNeuronInnovationNumber());
            genome.add(node);
        }
        for (int i = 0; i < inputNeurons; i++) {
            final Node node = new Node(NodeType.Input, historyList.getInputNeuronInnovationNumber(i));
            genome.add(node);
        }
        for (int i = 0; i < outputNeurons; i++) {
            final Node node = new Node(NodeType.Output, historyList.getOutputNeuronInnovationNumber(i), outputActivationFunction);
            genome.add(node);
        }
        for (int o = 0; o < outputNeurons; o++) {
            if (biasNeuron) {
                final Link link = new Link(historyList.getInputOutputLinkInnovation(-1, o, 0));
                link.setWeight(rng.nextDouble() * 3.0 - 1.5);
                genome.add(link);
            }
            final Link link = new Link(historyList.getInputOutputLinkInnovation(rng.nextInt(inputNeurons), o, 0));
            link.setWeight(rng.nextDouble() * 3.0 - 1.5);
            genome.add(link);
        }
        return genome;
    }

}

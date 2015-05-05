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
            for (int i = biasNeuron ? -1 : 0; i < inputNeurons; i++) {
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

    public static Genome createPerceptronGenome(boolean biasNeuron, int inputNeurons, int hiddenNeurons, int outputNeurons, ActivationFunction hiddenActivationFunction, ActivationFunction outputActivationFunction, Rng rng, HistoryList historyList) {
        Preconditions.checkArgument(inputNeurons > 0, "The parameter 'inputNeurons' has to be greater than zero");
        Preconditions.checkArgument(hiddenNeurons > 0, "The parameter 'hiddenNeurons' has to be greater than zero");
        Preconditions.checkArgument(outputNeurons > 0, "The parameter 'outputNeurons' has to be greater than zero");
        Preconditions.checkNotNull(hiddenActivationFunction, "The parameter 'hiddenActivationFunction' must not be null");
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
        for (int i = 0; i < hiddenNeurons; i++) {
            final Node node = new Node(NodeType.Hidden, historyList.getHiddenNeuronInnovationNumber(i), hiddenActivationFunction);
            genome.add(node);
        }
        for (int i = 0; i < outputNeurons; i++) {
            final Node node = new Node(NodeType.Output, historyList.getOutputNeuronInnovationNumber(i), outputActivationFunction);
            genome.add(node);
        }
        for (int h = 0; h < hiddenNeurons; h++) {
            for (int i = biasNeuron ? -1 : 0; i < inputNeurons; i++) {
                final Link link = new Link(historyList.getInputHiddenLinkInnovation(i, h, 0));
                link.setWeight(rng.nextDouble() * 3.0 - 1.5);
                genome.add(link);
            }
        }
        for (int o = 0; o < outputNeurons; o++) {
            if (biasNeuron) {
                final Link link = new Link(historyList.getInputOutputLinkInnovation(-1, o, 0));
                link.setWeight(rng.nextDouble() * 3.0 - 1.5);
                genome.add(link);
            }
            for (int h = 0; h < hiddenNeurons; h++) {
                final Link link = new Link(historyList.getHiddenOutputLinkInnovation(h, o, 0));
                link.setWeight(rng.nextDouble() * 3.0 - 1.5);
                genome.add(link);
            }
        }
        return genome;
    }

    public static Genome createDoublePerceptronGenome(boolean biasNeuron, int inputNeurons, int hiddenLayer1, int hiddenLayer2, int outputNeurons, ActivationFunction hiddenActivationFunction, ActivationFunction outputActivationFunction, Rng rng, HistoryList historyList) {
        Preconditions.checkArgument(inputNeurons > 0, "The parameter 'inputNeurons' has to be greater than zero");
        Preconditions.checkArgument(hiddenLayer1 > 0, "The parameter 'hiddenLayer1' has to be greater than zero");
        Preconditions.checkArgument(hiddenLayer2 > 0, "The parameter 'hiddenLayer2' has to be greater than zero");
        Preconditions.checkArgument(outputNeurons > 0, "The parameter 'outputNeurons' has to be greater than zero");
        Preconditions.checkNotNull(hiddenActivationFunction, "The parameter 'hiddenActivationFunction' must not be null");
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
        for (int i = 0; i < hiddenLayer1 + hiddenLayer2; i++) {
            final Node node = new Node(NodeType.Hidden, historyList.getHiddenNeuronInnovationNumber(i), hiddenActivationFunction);
            genome.add(node);
        }
        for (int i = 0; i < outputNeurons; i++) {
            final Node node = new Node(NodeType.Output, historyList.getOutputNeuronInnovationNumber(i), outputActivationFunction);
            genome.add(node);
        }
        for (int h = 0; h < hiddenLayer1; h++) {
            for (int i = biasNeuron ? -1 : 0; i < inputNeurons; i++) {
                final Link link = new Link(historyList.getInputHiddenLinkInnovation(i, h, 0));
                link.setWeight(rng.nextDouble() * 3.0 - 1.5);
                genome.add(link);
            }
        }
        for (int h2 = hiddenLayer1; h2 < hiddenLayer1 + hiddenLayer2; h2++) {
            if (biasNeuron) {
                final Link link = new Link(historyList.getInputHiddenLinkInnovation(-1, h2, 0));
                link.setWeight(rng.nextDouble() * 3.0 - 1.5);
                genome.add(link);
            }
            for (int h1 = 0; h1 < hiddenLayer1; h1++) {
                final Link link = new Link(historyList.getHiddenHiddenLinkInnovation(h1, h2, 0));
                link.setWeight(rng.nextDouble() * 3.0 - 1.5);
                genome.add(link);
            }
        }
        for (int o = 0; o < outputNeurons; o++) {
            if (biasNeuron) {
                final Link link = new Link(historyList.getInputOutputLinkInnovation(-1, o, 0));
                link.setWeight(rng.nextDouble() * 3.0 - 1.5);
                genome.add(link);
            }
            for (int h = hiddenLayer1; h < hiddenLayer1 + hiddenLayer2; h++) {
                final Link link = new Link(historyList.getHiddenOutputLinkInnovation(h, o, 0));
                link.setWeight(rng.nextDouble() * 3.0 - 1.5);
                genome.add(link);
            }
        }
        return genome;
    }

}

package ch.brotzilla.neat.experiments.test;

import java.util.Comparator;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import ch.brotzilla.neat.evolution.EvaluationStrategy;
import ch.brotzilla.neat.evolution.EvaluationStrategyProvider;
import ch.brotzilla.neat.evolution.EvolutionConfig;
import ch.brotzilla.neat.evolution.EvolutionEngine;
import ch.brotzilla.neat.evolution.EvolutionStrategy;
import ch.brotzilla.neat.evolution.Objective;
import ch.brotzilla.neat.evolution.PopulationProvider;
import ch.brotzilla.neat.evolution.Rng;
import ch.brotzilla.neat.evolution.SingleThreadingStrategy;
import ch.brotzilla.neat.evolution.Speciation;
import ch.brotzilla.neat.evolution.Specimen;
import ch.brotzilla.neat.evolution.StopCondition;
import ch.brotzilla.neat.evolution.speciation.NoSpeciationStrategy;
import ch.brotzilla.neat.expression.GenomeExpressor;
import ch.brotzilla.neat.expression.NEATGenomeExpressor;
import ch.brotzilla.neat.genome.Genome;
import ch.brotzilla.neat.genome.Genomes;
import ch.brotzilla.neat.genome.Link;
import ch.brotzilla.neat.history.HistoryList;
import ch.brotzilla.neat.math.ExtendedTanhFunction;
import ch.brotzilla.neat.neuralnet.NeuralNet;
import ch.brotzilla.util.MersenneTwister;

public class ExperimentTest {

    private final EvolutionEngine engine;
    
    private EvolutionConfig setupConfig() {
        return new EvolutionConfig.Builder()
            .setHistoryList(new HistoryList())
            .setObjectives(Objective.Minimize)
            .setPopulationSize(200)
            .setThreadingStrategy(new SingleThreadingStrategy())
            .setPopulationProvider(new TestPopulationProvider(new TestRng("test".hashCode())))
//            .setSpeciationStrategy(new KMeansSpeciationStrategy(10, 1, 100, true))
            .setSpeciationStrategy(new NoSpeciationStrategy())
            .setEvaluationStrategyProvider(new TestEvaluationStrategyProvider())
            .setEvolutionStrategy(new TestEvolutionStrategy(new TestRng("test2".hashCode())))
            .setStopCondition(new TestStopCondition())
            .build();
    }
    
    public ExperimentTest() {
        engine = new EvolutionEngine(setupConfig());
    }

    public void start() {
        engine.evolve();
    }
    
    public static void main(String[] args) {
        final ExperimentTest test = new ExperimentTest();
        test.start();
    }

    private static class TestRng implements Rng {

        private final MersenneTwister rng;
        
        public TestRng(long seed) {
            rng = new MersenneTwister(seed);
        }
        
        public int nextInt() {
            return rng.nextInt();
        }

        public int nextInt(int n) {
            return rng.nextInt(n);
        }

        public int nextInt(int low, int high) {
            throw new UnsupportedOperationException();
        }

        public double nextDouble() {
            return rng.nextDouble();
        }
        
    }

    private static class TestPopulationProvider implements PopulationProvider {

        private final Rng rng;
        
        public TestPopulationProvider(Rng rng) {
            Preconditions.checkNotNull(rng, "The parameter 'rng' must not be null");
            this.rng = rng;
        }
        
        public List<Specimen> providePopulation(EvolutionConfig config) {
            final List<Specimen> population = Lists.newArrayList();
            for (int i = 0; i < config.getPopulationSize(); i++) {
                population.add(new Specimen(config.getObjectives(), Genomes.createDoublePerceptronGenome(true, 8, 20, 20, 1, new ExtendedTanhFunction(), new ExtendedTanhFunction(), rng, config.getHistoryList())));
//                population.add(new Specimen(config.getObjectives(), Genomes.createPerceptronGenome(true, 7, 10, 1, new ExtendedElliottFunction(), new ExtendedElliottFunction(), rng, config.getHistoryList())));
//                population.add(new Specimen(config.getObjectives(), Genomes.createFeatureSelectiveGenome(true, 5, 1, new ExtendedElliottFunction(), rng, config.getHistoryList())));
            }
            return population;
        }

    }

    private static class TestEvaluationStrategyProvider implements EvaluationStrategyProvider {

        private static TestEvaluationStrategy instance = new TestEvaluationStrategy();
        
        public EvaluationStrategy provide(EvolutionConfig config) {
            return instance;
        }

    }
    
    private static class TestEvaluationStrategy implements EvaluationStrategy {

        private static final GenomeExpressor exp = new NEATGenomeExpressor();
        private static final String table = "ABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜabcdefghijklmnopqrstuvwxyzäöü0123456789!?:.,_\\/*-+&%<>'()\" ";
        private static final String target = "I Love Patrizia! And tat's the truth! :)";
        private static final double[] targetOutput = computeTargetOutput(table, target);
        
        private double bestFitness = Double.MAX_VALUE;
        
        private static double[] computeTargetOutput(String table, String value) {
            Preconditions.checkNotNull(table, "The parameter 'table' must not be null");
            Preconditions.checkNotNull(value, "The parameter 'value' must not be null");
            final double slot = 1.0 / table.length();
            final double halfSlot = slot * 0.5;
            final double[] result = new double[value.length()];
            for (int i = 0; i < result.length; i++) {
                final char c = value.charAt(i);
                final int idx = table.indexOf(c);
                Preconditions.checkArgument(idx >= 0, "The parameter 'value' must not contain '" + c + "'");
                result[i] = idx * slot + halfSlot;
            }
            return result;
        }
        
        private boolean isVovel(char c) {
            c = Character.toLowerCase(c);
            return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u' || c == 'ä' || c == 'ö' || c == 'ü';
        }
        
        private double scale(double output) {
            output = output < 0.0 ? -output : output;
            if (output < 0.25) output = 0.25;
            if (output > 0.75) output = 0.75;
            output = (output - 0.25) * 2;
            return output;
        }
        
        public double[] compute(NeuralNet nn) {
            Preconditions.checkNotNull(nn, "The genome expressor must not return null");
            Preconditions.checkState(nn.getNumberOfInputNeurons() == 8);
            Preconditions.checkState(nn.getNumberOfOutputNeurons() == 1);
            final double[] result = new double[target.length()], input = new double[8], hidden = new double[nn.getNumberOfHiddenNeurons()], output = new double[1];
            for (int i = 0; i < target.length(); i++) {
                final char c = target.charAt(i);
                input[0] = (i + 1) / target.length();
                input[1] = (target.length() - i) / target.length();
                input[2] = Character.isUpperCase(c) ? 1 : -1;
                input[3] = Character.isLetter(c) ? 1 : -1;
                input[4] = Character.isDigit(c) ? 1 : -1;
                input[5] = isVovel(c) ? 1 : -1;
                input[6] = i - 1 < 0 ? 0 : targetOutput[i] - targetOutput[i-1];
                input[7] = i + 1 == target.length() ? 0 : targetOutput[i] - targetOutput[i+1];
                nn.compute(input, hidden, output);
                result[i] = scale(output[0]);
            }
            return result;
        }
        
        public double distance(double[] value) {
           Preconditions.checkNotNull(value, "The parameter 'value' must not be null");
           Preconditions.checkArgument(targetOutput.length == value.length, "The length of the parameter 'value' has to be equal to " + targetOutput.length);
           double result = 0;
           for (int i = 0; i < targetOutput.length; i++) {
               final double d = (targetOutput[i] - value[i]) * table.length();
               result += d * d;
           }
           return result;
        }
        
        public String render(double[] output) {
            final StringBuilder b = new StringBuilder();
            for (int i = 0; i < output.length; i++) {
                final int index = (int) Math.round(output[i] * (table.length() - 1));
                b.append(table.charAt(index));
            }
            return b.toString();
        }
        
        public void evaluate(Specimen specimen, EvolutionConfig config) {
            Preconditions.checkNotNull(specimen, "The parameter 'specimen' must not be null");
            Preconditions.checkNotNull(config, "The parameter 'config' must not be null");
            final NeuralNet nn =  exp.express(specimen.getGenome());
            final double[] output = compute(nn);
            final double distance = distance(output);
            specimen.setObjective(0, distance);
            if (distance < bestFitness) {
                bestFitness = distance;
                System.out.println(render(output) + " -- distance = " + distance);
            }
        }
        
    }

    private static class TestEvolutionStrategy implements EvolutionStrategy {

        private final Rng rng;
        
        public TestEvolutionStrategy(Rng rng) {
            Preconditions.checkNotNull(rng, "The parameter 'rng' must not be null");
            this.rng = rng;
        }
        
        public List<Specimen> evolve(Speciation speciation, EvolutionConfig config) {
            final List<Specimen> parents = speciation.getSpecies(0).getSpecimens();
            final List<Specimen> children = Lists.newArrayList();
            speciation.sortAllSpecies(new TestComparator());
            for (int i = 0; i < 10; i++) {
                children.add(parents.get(i).clone());
            }
            fill(parents, children);
            return children;
        }

        private double computeTotalFitness(List<Specimen> parents) {
            Preconditions.checkNotNull(parents, "The parameter 'parents' must not be null");
            double result = 0;
            for (final Specimen specimen : parents) {
                result += 1.0 / specimen.getFitness();
            }
            return result;
        }
        
        private double[] computeSlots(List<Specimen> parents, double total) {
            Preconditions.checkNotNull(parents, "The parameter 'parents' must not be null");
            final double[] result = new double[parents.size()];
            double sum = 0;
            for (int i = 0; i < result.length; i++) {
                sum += (1.0 / parents.get(i).getFitness()) / total;
                result[i] = sum;
            }
            return result;
        }
        
        private void fill(List<Specimen> parents, List<Specimen> children) {
            Preconditions.checkNotNull(parents, "The parameter 'parents' must not be null");
            Preconditions.checkNotNull(children, "The parameter 'children' must not be null");
            final int count = parents.size() - children.size();
            final double pointerSize = rng.nextDouble() / count;
            final double total = computeTotalFitness(parents);
            final double[] slots = computeSlots(parents, total);
            int slot = 0;
            for (int i = 0; i < count; i++) {
                final double pointer = pointerSize * (i + 1);
                while (slots[slot] <= pointer) {
                    ++slot;
                }
                children.add(mutate(parents.get(slot)));
            }
        }
        
        private double gaussianMutation(double mean, double stddev) {
            double x1 = rng.nextDouble();
            double x2 = rng.nextDouble();
            if(x1 == 0) x1 = 1;
            if(x2 == 0) x2 = 1;
            double y1 = Math.sqrt(-2.0 * Math.log(x1)) * Math.cos(2.0 * Math.PI * x2);
            return y1 * stddev + mean;
        }
        
        private double adaptSigma(double sigma) {
            return Math.max(0, sigma * Math.exp(gaussianMutation(0, 1)));
        }

        private Specimen mutate(Specimen specimen) {
            final Genome genome = specimen.getGenome().clone();
            genome.setSigma(adaptSigma(genome.getSigma()));
            final int count = rng.nextInt(2) + 1;
            for (int i = 0; i < count; i++) {
                final Link link = genome.getLinkByIndex(rng.nextInt(genome.getLinks().size()));
                link.setWeight(gaussianMutation(link.getWeight(), genome.getSigma()));
            }
            return new Specimen(specimen.getNumberOfObjectives(), genome);
        }
        
        private static class TestComparator implements Comparator<Specimen> {

            public int compare(Specimen a, Specimen b) {
                if (a.getFitness() < b.getFitness()) {
                    return -1;
                }
                if (a.getFitness() > b.getFitness()) {
                    return 1;
                }
                return 0;
            }
            
        }

    }

    private static class TestStopCondition implements StopCondition {

        public boolean isSatisfied() {
            return false;
        }

    }

}

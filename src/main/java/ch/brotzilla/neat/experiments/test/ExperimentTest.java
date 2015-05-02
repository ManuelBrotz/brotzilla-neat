package ch.brotzilla.neat.experiments.test;

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
import ch.brotzilla.neat.evolution.speciation.KMeansSpeciationStrategy;
import ch.brotzilla.neat.genome.Genomes;
import ch.brotzilla.neat.history.HistoryList;
import ch.brotzilla.neat.math.ExtendedElliottFunction;
import ch.brotzilla.util.MersenneTwister;

public class ExperimentTest {

    private final EvolutionEngine engine;
    
    private EvolutionConfig setupConfig() {
        return new EvolutionConfig.Builder()
            .setHistoryList(new HistoryList())
            .setObjectives(Objective.Minimize)
            .setPopulationSize(100)
            .setThreadingStrategy(new SingleThreadingStrategy())
            .setPopulationProvider(new TestPopulationProvider(new TestRng("test".hashCode())))
            .setSpeciationStrategy(new KMeansSpeciationStrategy())
            .setEvaluationStrategyProvider(new TestEvaluationStrategyProvider())
            .setEvolutionStrategy(new TestEvolutionStrategy())
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
                population.add(new Specimen(config.getObjectives(), Genomes.createFeatureSelectiveGenome(true, 4, 2, new ExtendedElliottFunction(), rng, config.getHistoryList())));
            }
            return population;
        }

    }

    private static class TestEvaluationStrategyProvider implements EvaluationStrategyProvider {

        public EvaluationStrategy provide(EvolutionConfig config) {
            return new TestEvaluationStrategy();
        }

    }
    
    private static class TestEvaluationStrategy implements EvaluationStrategy {

        public void evaluate(Specimen specimen, EvolutionConfig config) {
            
        }
        
    }

    private static class TestEvolutionStrategy implements EvolutionStrategy {

        public List<Specimen> evolve(Speciation speciation, EvolutionConfig config) {
            return speciation.getSpecies(0).getSpecimens();
        }

    }

    private static class TestStopCondition implements StopCondition {

        public boolean isSatisfied() {
            return false;
        }

    }

}

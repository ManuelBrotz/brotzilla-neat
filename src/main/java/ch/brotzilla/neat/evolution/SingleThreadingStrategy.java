package ch.brotzilla.neat.evolution;

public class SingleThreadingStrategy implements ThreadingStrategy {

    public SingleThreadingStrategy() {}

    public void evaluate(Population population, EvolutionConfig config) {
        final GenomeEvaluator evaluator = config.getEvolutionStrategy().provideGenomeEvaluator(config);
        for (final Individual individual : population.getIndividuals()) {
            evaluator.evaluate(individual, config);
        }
    }

}

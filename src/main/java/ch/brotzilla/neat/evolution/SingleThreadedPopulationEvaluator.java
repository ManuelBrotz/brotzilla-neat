package ch.brotzilla.neat.evolution;

public class SingleThreadedPopulationEvaluator implements PopulationEvaluator {

    public SingleThreadedPopulationEvaluator() {}

    public void evaluate(Population population, EvolutionConfig config) {
        final FitnessEvaluator evaluator = config.getFitnessEvaluatorProvider().provideEvaluator(config);
        for (final Individual individual : population) {
            evaluator.evaluate(individual, config);
        }
    }

}

package ch.brotzilla.neat.evolution;

import java.util.List;

public class SingleThreadingStrategy implements ThreadingStrategy {

    public SingleThreadingStrategy() {}

    public void run(List<Specimen> population, EvolutionConfig config) {
        final EvaluationStrategy evaluationStrategy = config.getEvaluationStrategyProvider().provide(config);
        for (final Specimen specimen : population) {
            evaluationStrategy.evaluate(specimen, config);
        }
    }

}

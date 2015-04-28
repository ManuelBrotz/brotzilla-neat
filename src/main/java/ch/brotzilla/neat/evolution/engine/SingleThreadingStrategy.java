package ch.brotzilla.neat.evolution.engine;

import java.util.List;

import ch.brotzilla.neat.evolution.EvolutionConfig;
import ch.brotzilla.neat.evolution.EvaluationStrategy;
import ch.brotzilla.neat.evolution.Specimen;
import ch.brotzilla.neat.evolution.ThreadingStrategy;

public class SingleThreadingStrategy implements ThreadingStrategy {

    public SingleThreadingStrategy() {}

    public void run(List<Specimen> population, EvolutionConfig config) {
        final EvaluationStrategy evaluationStrategy = config.getEvaluationStrategyProvider().provide(config);
        for (final Specimen specimen : population) {
            evaluationStrategy.evaluate(specimen, config);
        }
    }

}

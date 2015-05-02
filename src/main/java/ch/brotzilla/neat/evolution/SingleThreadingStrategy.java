package ch.brotzilla.neat.evolution;

import java.util.List;

import com.google.common.base.Preconditions;

public class SingleThreadingStrategy implements ThreadingStrategy {

    public SingleThreadingStrategy() {}

    public void run(List<Specimen> population, EvolutionConfig config) {
        final EvaluationStrategy evaluationStrategy = config.getEvaluationStrategyProvider().provide(config);
        Preconditions.checkNotNull(evaluationStrategy, "The evaluation strategy provider must not return null");
        for (final Specimen specimen : population) {
            evaluationStrategy.evaluate(specimen, config);
        }
    }

}

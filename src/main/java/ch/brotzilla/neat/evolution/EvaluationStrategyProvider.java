package ch.brotzilla.neat.evolution;

public interface EvaluationStrategyProvider {

    EvaluationStrategy provide(EvolutionConfig config);
    
}

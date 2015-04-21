package ch.brotzilla.neat.evolution;

public interface FitnessEvaluatorProvider {

    FitnessEvaluator provideEvaluator(EvolutionConfig config);
    
}

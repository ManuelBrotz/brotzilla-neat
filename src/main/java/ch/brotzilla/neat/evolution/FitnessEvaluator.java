package ch.brotzilla.neat.evolution;

public interface FitnessEvaluator {
    
    void evaluate(Individual individual, EvolutionConfig config);
    
}

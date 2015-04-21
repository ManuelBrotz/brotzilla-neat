package ch.brotzilla.neat.evolution;

public interface EvolutionStrategy {

    Population evolve(Population population, EvolutionConfig config);
    
}

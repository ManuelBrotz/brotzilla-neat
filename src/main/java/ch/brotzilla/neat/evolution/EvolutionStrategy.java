package ch.brotzilla.neat.evolution;

public interface EvolutionStrategy {

	Population provideInitialPopulation(EvolutionConfig config);
    
    Population evolve(Population population, EvolutionConfig config);

}

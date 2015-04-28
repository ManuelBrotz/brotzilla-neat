package ch.brotzilla.neat.evolution;

public interface EvolutionStrategy {

    Objectives getObjectives();
    
    GenomeEvaluator provideGenomeEvaluator(EvolutionConfig config);

    Population provideInitialPopulation(EvolutionConfig config);
    
    Population evolvePopulation(Population population, EvolutionConfig config);
    
}

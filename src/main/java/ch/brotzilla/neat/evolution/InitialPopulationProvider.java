package ch.brotzilla.neat.evolution;

public interface InitialPopulationProvider {

    Population providePopulation(EvolutionConfig config);
    
}

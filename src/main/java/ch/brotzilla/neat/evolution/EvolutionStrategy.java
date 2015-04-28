package ch.brotzilla.neat.evolution;

import java.util.List;

public interface EvolutionStrategy {

    List<Specimen> evolve(List<Specimen> population, Speciation speciation, EvolutionConfig config);
    
}

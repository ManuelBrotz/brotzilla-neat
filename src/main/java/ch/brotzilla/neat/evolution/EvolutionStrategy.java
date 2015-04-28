package ch.brotzilla.neat.evolution;

import java.util.List;

public interface EvolutionStrategy {

    List<Specimen> evolve(Speciation speciation, EvolutionConfig config);
    
}

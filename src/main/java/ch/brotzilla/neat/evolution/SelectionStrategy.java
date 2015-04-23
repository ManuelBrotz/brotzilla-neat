package ch.brotzilla.neat.evolution;

import java.util.List;

public interface SelectionStrategy {
    
    List<Individual> select(Population population, EvolutionConfig config);
    
}

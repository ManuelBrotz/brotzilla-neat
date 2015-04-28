package ch.brotzilla.neat.evolution;

import java.util.List;

public interface ThreadingStrategy {

    void run(List<Specimen> population, EvolutionConfig config);
    
}

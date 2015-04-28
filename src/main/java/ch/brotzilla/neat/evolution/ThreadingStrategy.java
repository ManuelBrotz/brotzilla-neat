package ch.brotzilla.neat.evolution;

public interface ThreadingStrategy {

    void evaluate(Population population, EvolutionConfig config);
    
}

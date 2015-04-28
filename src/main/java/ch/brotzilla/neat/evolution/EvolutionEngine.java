package ch.brotzilla.neat.evolution;

import com.google.common.base.Preconditions;

public class EvolutionEngine {

    private final EvolutionConfig config;
    
    public EvolutionEngine(EvolutionConfig config) {
        Preconditions.checkNotNull(config, "The parameter 'config' must not be null");
        this.config = config;
    }
    
    public EvolutionConfig getConfig() {
        return config;
    }
    
    public void evolve() {
        
        final int numberOfObjectives = config.getEvolutionStrategy().getObjectives().getNumberOfObjectives();
        final int populationSize = config.getPopulationSize();
        
        Population population = config.getEvolutionStrategy().provideInitialPopulation(config);

        Preconditions.checkNotNull(population, "The population must not be null");
        Preconditions.checkState(population.getNumberOfObjectives() == numberOfObjectives, "The population's number of objectives has to be equal to " + numberOfObjectives);
        Preconditions.checkState(population.getSpecimens().size() == populationSize, "The population has to be of size " + populationSize);
        
        do {

            config.getThreadingStrategy().evaluate(population, config);
            population = config.getEvolutionStrategy().evolvePopulation(population, config);

            Preconditions.checkNotNull(population, "The population must not be null");
            Preconditions.checkState(population.getNumberOfObjectives() == numberOfObjectives, "The population's number of objectives has to be equal to " + numberOfObjectives);
            Preconditions.checkState(population.getSpecimens().size() == populationSize, "The population has to be of size " + populationSize);

        } while (!config.getStopCondition().isSatisfied());
        
    }

}

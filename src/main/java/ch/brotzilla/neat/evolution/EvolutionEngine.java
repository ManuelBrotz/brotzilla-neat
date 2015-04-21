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
        
        Population population = config.getInitialPopulationProvider().providePopulation(config);

        Preconditions.checkNotNull(population, "The population must not be null");
        Preconditions.checkState(population.getNumberOfObjectives() == config.getNumberOfObjectives(), "The population's number of objectives has to be equal to " + config.getNumberOfObjectives());
        Preconditions.checkState(population.size() == config.getPopulationSize(), "The population has to be of size " + config.getPopulationSize());
        
        do {

            config.getPopulationEvaluator().evaluate(population, config);
            population = config.getEvolutionStrategy().evolve(population, config);

            Preconditions.checkNotNull(population, "The population must not be null");
            Preconditions.checkState(population.getNumberOfObjectives() == config.getNumberOfObjectives(), "The population's number of objectives has to be equal to " + config.getNumberOfObjectives());
            Preconditions.checkState(population.size() == config.getPopulationSize(), "The population has to be of size " + config.getPopulationSize());

        } while (!config.getStopCondition().isSatisfied());
        
    }

}

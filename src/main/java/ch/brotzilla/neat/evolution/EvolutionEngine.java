package ch.brotzilla.neat.evolution;

import java.util.List;

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
        
        List<Specimen> population = config.getPopulationProvider().providePopulation(config);
        Preconditions.checkNotNull(population, "The population provider must not return null");
        Preconditions.checkArgument(population.size() == config.getPopulationSize(), "The population provider has to return " + config.getPopulationSize() + " specimens");
        
        Speciation speciation = null;
        
        do {
            config.getThreadingStrategy().run(population, config);

            speciation = config.getSpeciationStrategy().speciate(speciation, population);
            Preconditions.checkNotNull(speciation, "The speciation strategy must not return null");
            Preconditions.checkState(speciation.getSpecies().size() > 0, "The speciation strategy has to return at least one species");
            
            population = config.getEvolutionStrategy().evolve(speciation, config);
            Preconditions.checkNotNull(population, "The evolution strategy must not return null");
            Preconditions.checkArgument(population.size() == config.getPopulationSize(), "The evolution strategy has to return " + config.getPopulationSize() + " specimens");

        } while (!config.getStopCondition().isSatisfied());
        
    }

}

package ch.brotzilla.neat.evolution;

import com.google.common.base.Preconditions;

public class EvolutionConfig {

    private int numberOfObjectives;
    private int populationSize;
    private double eliteFraction;
    private InitialPopulationProvider initialPopulationProvider;
    private PopulationEvaluator populationEvaluator;
    private FitnessEvaluatorProvider fitnessEvaluatorProvider;
    private EvolutionStrategy evolutionStrategy;
    private StopCondition stopCondition;
    
    private EvolutionConfig() {}
    
    public EvolutionConfig(EvolutionConfig source) {
        Preconditions.checkNotNull(source, "The parameter 'source' must not be null");
        numberOfObjectives = source.numberOfObjectives;
        populationSize = source.populationSize;
        eliteFraction = source.eliteFraction;
        initialPopulationProvider = source.initialPopulationProvider;
        populationEvaluator = source.populationEvaluator;
        fitnessEvaluatorProvider = source.fitnessEvaluatorProvider;
        evolutionStrategy = source.evolutionStrategy;
        stopCondition = source.stopCondition;
    }
    
    public int getNumberOfObjectives() {
        return numberOfObjectives;
    }
    
    public int getPopulationSize() {
        return populationSize;
    }
    
    public double getEliteFraction() {
        return eliteFraction;
    }
    
    public int getEliteSize() {
        return (int) Math.round(populationSize * eliteFraction);
    }
    
    public InitialPopulationProvider getInitialPopulationProvider() {
        return initialPopulationProvider;
    }
    
    public PopulationEvaluator getPopulationEvaluator() {
        return populationEvaluator;
    }
    
    public FitnessEvaluatorProvider getFitnessEvaluatorProvider() {
        return fitnessEvaluatorProvider;
    }
    
    public EvolutionStrategy getEvolutionStrategy() {
        return evolutionStrategy;
    }
    
    public StopCondition getStopCondition() {
        return stopCondition;
    }

    public static class Builder {
        
        private EvolutionConfig config;
        
        public Builder() {
            config = new EvolutionConfig();
        }
        
        public Builder setNumberOfObjectives(int numberOfObjectives) {
            Preconditions.checkArgument(numberOfObjectives > 0, "The parameter 'numberOfObjectives' has to be greater than zero");
            config.numberOfObjectives = numberOfObjectives;
            return this;
        }
        
        public Builder setPopulationSize(int populationSize) {
            Preconditions.checkArgument(populationSize > 0, "The parameter 'populationSize' has to be greater than zero");
            config.populationSize = populationSize;
            return this;
        }
        
        public Builder setEliteFraction(double eliteFraction) {
            Preconditions.checkArgument(eliteFraction > 0, "The parameter 'eliteFraction' has to be greater than zero");
            config.eliteFraction = eliteFraction;
            return this;
        }
        
        public Builder setInitialPopulationProvider(InitialPopulationProvider initialPopulationProvider) {
            Preconditions.checkNotNull(initialPopulationProvider, "The parameter 'initialPopulationProvider' must not be null");
            config.initialPopulationProvider = initialPopulationProvider;
            return this;
        }
        
        public Builder setPopulationEvaluator(PopulationEvaluator populationEvaluator) {
            Preconditions.checkNotNull(populationEvaluator, "The parameter 'populationEvaluator' must not be null");
            config.populationEvaluator = populationEvaluator;
            return this;
        }
        
        public Builder setFitnessEvaluatorProvider(FitnessEvaluatorProvider fitnessEvaluatorProvider) {
            Preconditions.checkNotNull(fitnessEvaluatorProvider, "The parameter 'fitnessEvaluatorProvider' must not be null");
            config.fitnessEvaluatorProvider = fitnessEvaluatorProvider;
            return this;
        }
        
        public Builder setEvolutionStrategy(EvolutionStrategy evolutionStrategy) {
            Preconditions.checkNotNull(evolutionStrategy, "The parameter 'evolutionStrategy' must not be null");
            config.evolutionStrategy = evolutionStrategy;
            return this;
        }
        
        public Builder setStopCondition(StopCondition stopCondition) {
            Preconditions.checkNotNull(stopCondition, "The parameter 'stopCondition' must not be null");
            config.stopCondition = stopCondition;
            return this;
        }
        
        public EvolutionConfig build() {
            Preconditions.checkArgument(config.numberOfObjectives > 0, "The property 'numberOfObjectives' has to be greater than zero");
            Preconditions.checkArgument(config.eliteFraction >= 0, "The property 'eliteFraction' has to be greater than or equal to zero");
            Preconditions.checkArgument(config.eliteFraction < 1.0, "The property 'eliteFraction' has to be less than 1.0");
            Preconditions.checkArgument(config.populationSize > config.getEliteSize(), "The property 'populationSize' has to be greater than the property 'eliteSize'");
            Preconditions.checkNotNull(config.initialPopulationProvider, "The property 'initialPopulationProvider' must not be null");
            Preconditions.checkNotNull(config.populationEvaluator, "The property 'populationEvaluator' must not be null");
            Preconditions.checkNotNull(config.fitnessEvaluatorProvider, "The property 'fitnessEvaluatorProvider' must not be null");
            Preconditions.checkNotNull(config.evolutionStrategy, "The property 'evolutionStrategy' must not be null");
            Preconditions.checkNotNull(config.stopCondition, "The property 'stopCondition' must not be null");
            return new EvolutionConfig(config);
        }
        
    }
    
}

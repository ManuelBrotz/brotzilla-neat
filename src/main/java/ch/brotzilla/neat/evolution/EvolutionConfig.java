package ch.brotzilla.neat.evolution;

import com.google.common.base.Preconditions;

public class EvolutionConfig {

	private RngProvider rngProvider;
    private Objectives objectives;
    private int populationSize;
    private double eliteFraction, selectFraction;
    private PopulationEvaluator populationEvaluator;
    private FitnessEvaluatorProvider fitnessEvaluatorProvider;
    private EvolutionStrategy evolutionStrategy;
    private StopCondition stopCondition;
    
    private EvolutionConfig() {}
    
    private EvolutionConfig(EvolutionConfig source) {
        Preconditions.checkNotNull(source, "The parameter 'source' must not be null");
        rngProvider = source.rngProvider;
        objectives = source.objectives;
        populationSize = source.populationSize;
        eliteFraction = source.eliteFraction;
        selectFraction = source.selectFraction;
        populationEvaluator = source.populationEvaluator;
        fitnessEvaluatorProvider = source.fitnessEvaluatorProvider;
        evolutionStrategy = source.evolutionStrategy;
        stopCondition = source.stopCondition;
    }
    
    public RngProvider getRngProvider() {
    	return rngProvider;
    }
    
    public Objectives getObjectives() {
        return objectives;
    }
    
    public Objective getObjective(int index) {
        return objectives.get(index);
    }
    
    public int getNumberOfObjectives() {
        return objectives.getNumberOfObjectives();
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
    
    public double getSelectFraction() {
        return selectFraction;
    }
    
    public int getSelectSize() {
        return (int) Math.round(populationSize * selectFraction);
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
        
        public Builder setRngProvider(RngProvider rngProvider) {
        	Preconditions.checkNotNull(rngProvider, "The parameter 'rngProvider' must not be zero");
        	config.rngProvider = rngProvider;
        	return this;
        }
        
        public Builder setObjectives(Objectives objectives) {
            Preconditions.checkNotNull(objectives, "The parameter 'objectives' must not be null");
            config.objectives = objectives;
            return this;
        }
        
        public Builder setObjectives(Objective... objectives) {
            return setObjectives(new Objectives(objectives));
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
        
        public Builder setSelectFraction(double selectFraction) {
            Preconditions.checkArgument(selectFraction > 0, "The parameter 'selectFraction' has to be greater than zero");
            config.selectFraction = selectFraction;
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
        	Preconditions.checkNotNull(config.rngProvider, "The property 'rngProvider' must not be zero");
            Preconditions.checkNotNull(config.objectives, "The property 'objectives' must not be null");
            
            Preconditions.checkArgument(config.eliteFraction >= 0, "The property 'eliteFraction' has to be greater than or equal to zero");
            Preconditions.checkArgument(config.eliteFraction < 1.0, "The property 'eliteFraction' has to be less than 1.0");
            Preconditions.checkArgument(config.populationSize > config.getEliteSize(), "The property 'populationSize' has to be greater than the property 'eliteSize'");
            
            Preconditions.checkArgument(config.selectFraction > 0, "The property 'selectFraction' has to be greater than zero");
            Preconditions.checkArgument(config.selectFraction < 1.0, "The property 'selectFraction' has to be less than 1.0");
            Preconditions.checkArgument(config.getSelectSize() > 0, "The property 'selectSize' has to be greater than zero");
            Preconditions.checkArgument(config.populationSize > config.getSelectSize(), "The property 'populationSize' has to be greater than the property 'selectSize'");

            Preconditions.checkNotNull(config.populationEvaluator, "The property 'populationEvaluator' must not be null");
            Preconditions.checkNotNull(config.fitnessEvaluatorProvider, "The property 'fitnessEvaluatorProvider' must not be null");
            Preconditions.checkNotNull(config.evolutionStrategy, "The property 'evolutionStrategy' must not be null");
            Preconditions.checkNotNull(config.stopCondition, "The property 'stopCondition' must not be null");
            
            return new EvolutionConfig(config);
        }
        
    }
    
}

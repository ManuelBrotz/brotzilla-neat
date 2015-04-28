package ch.brotzilla.neat.evolution;

import com.google.common.base.Preconditions;

public class EvolutionConfig {

    private Objectives objectives;
    private RngProvider rngProvider;
    private int populationSize;
    private ThreadingStrategy threadingStrategy;
    private PopulationProvider populationProvider;
    private SpeciationStrategy speciationStrategy;
    private EvaluationStrategyProvider evaluationStrategyProvider;
    private EvolutionStrategy evolutionStrategy;
    private StopCondition stopCondition;
    
    private EvolutionConfig() {}
    
    private EvolutionConfig(EvolutionConfig source) {
        Preconditions.checkNotNull(source, "The parameter 'source' must not be null");
        objectives = source.objectives;
        rngProvider = source.rngProvider;
        populationSize = source.populationSize;
        threadingStrategy = source.threadingStrategy;
        populationProvider = source.populationProvider;
        speciationStrategy = source.speciationStrategy;
        evaluationStrategyProvider = source.evaluationStrategyProvider;
        evolutionStrategy = source.evolutionStrategy;
        stopCondition = source.stopCondition;
    }
    
    public Objectives getObjectives() {
        return objectives;
    }
    
    public RngProvider getRngProvider() {
    	return rngProvider;
    }
    
    public int getPopulationSize() {
        return populationSize;
    }
    
    public ThreadingStrategy getThreadingStrategy() {
        return threadingStrategy;
    }
    
    public PopulationProvider getPopulationProvider() {
        return populationProvider;
    }
    
    public SpeciationStrategy getSpeciationStrategy() {
        return speciationStrategy;
    }
    
    public EvaluationStrategyProvider getEvaluationStrategyProvider() {
        return evaluationStrategyProvider;
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
        
        public Builder setObjectives(Objectives objectives) {
            Preconditions.checkNotNull(objectives, "The parameter 'objectives' must not be null");
            config.objectives = objectives;
            return this;
        }
        
        public Builder setObjectives(Objective... objectives) {
            Preconditions.checkNotNull(objectives, "The parameter 'objectives' must not be null");
            config.objectives = new Objectives(objectives);
            return this;
        }
        
        public Builder setRngProvider(RngProvider rngProvider) {
        	Preconditions.checkNotNull(rngProvider, "The parameter 'rngProvider' must not be zero");
        	config.rngProvider = rngProvider;
        	return this;
        }
        
        public Builder setPopulationSize(int populationSize) {
            Preconditions.checkArgument(populationSize > 0, "The parameter 'populationSize' has to be greater than zero");
            config.populationSize = populationSize;
            return this;
        }
        
        public Builder setThreadingStrategy(ThreadingStrategy threadingStrategy) {
            Preconditions.checkNotNull(threadingStrategy, "The parameter 'threadingStrategy' must not be null");
            config.threadingStrategy = threadingStrategy;
            return this;
        }
        
        public Builder setPopulationProvider(PopulationProvider populationProvider) {
            Preconditions.checkNotNull(populationProvider, "The parameter 'populationProvider' must not be null");
            config.populationProvider = populationProvider;
            return this;
        }
        
        public Builder setSpeciationStrategy(SpeciationStrategy speciationStrategy) {
            Preconditions.checkNotNull(speciationStrategy, "The parameter 'speciationStrategy' must not be null");
            config.speciationStrategy = speciationStrategy;
            return this;
        }
        
        public Builder setEvaluationStrategyProvider(EvaluationStrategyProvider evaluationStrategyProvider) {
            Preconditions.checkNotNull(evaluationStrategyProvider, "The parameter 'evaluationStrategyProvider' must not be null");
            config.evaluationStrategyProvider = evaluationStrategyProvider;
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
            Preconditions.checkNotNull(config.objectives, "The property 'objectives' must not be null");
        	Preconditions.checkNotNull(config.rngProvider, "The property 'rngProvider' must not be zero");
            Preconditions.checkArgument(config.populationSize > 0, "The property 'populationSize' has to be greater than zero");
            Preconditions.checkNotNull(config.threadingStrategy, "The property 'threadingStrategy' must not be null");
            Preconditions.checkNotNull(config.populationProvider, "The property 'populationProvider' must not be null");
            Preconditions.checkNotNull(config.speciationStrategy, "The property 'speciationStrategy' must not be null");
            Preconditions.checkNotNull(config.evaluationStrategyProvider, "The property 'evaluationStrategyProvider' must not be null");
            Preconditions.checkNotNull(config.evolutionStrategy, "The property 'evolutionStrategy' must not be null");
            Preconditions.checkNotNull(config.stopCondition, "The property 'stopCondition' must not be null");
            return new EvolutionConfig(config);
        }
        
    }
    
}

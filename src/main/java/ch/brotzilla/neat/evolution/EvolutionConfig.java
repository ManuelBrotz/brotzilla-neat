package ch.brotzilla.neat.evolution;

import com.google.common.base.Preconditions;

public class EvolutionConfig {

    private RngProvider rngProvider;
    private int populationSize;
    private ThreadingStrategy threadingStrategy;
    private EvolutionStrategy evolutionStrategy;
    private StopCondition stopCondition;
    
    private EvolutionConfig() {}
    
    private EvolutionConfig(EvolutionConfig source) {
        Preconditions.checkNotNull(source, "The parameter 'source' must not be null");
        rngProvider = source.rngProvider;
        populationSize = source.populationSize;
        threadingStrategy = source.threadingStrategy;
        evolutionStrategy = source.evolutionStrategy;
        stopCondition = source.stopCondition;
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
        
        public Builder setPopulationSize(int populationSize) {
            Preconditions.checkArgument(populationSize > 0, "The parameter 'populationSize' has to be greater than zero");
            config.populationSize = populationSize;
            return this;
        }
        
        public Builder setThreadingStrategy(ThreadingStrategy populationEvaluator) {
            Preconditions.checkNotNull(populationEvaluator, "The parameter 'populationEvaluator' must not be null");
            config.threadingStrategy = populationEvaluator;
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
            Preconditions.checkArgument(config.populationSize > 0, "The property 'populationSize' has to be greater than zero");
            Preconditions.checkNotNull(config.threadingStrategy, "The property 'populationEvaluator' must not be null");
            Preconditions.checkNotNull(config.evolutionStrategy, "The property 'evolutionStrategy' must not be null");
            Preconditions.checkNotNull(config.stopCondition, "The property 'stopCondition' must not be null");
            return new EvolutionConfig(config);
        }
        
    }
    
}

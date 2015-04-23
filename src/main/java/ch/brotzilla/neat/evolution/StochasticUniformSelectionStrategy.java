package ch.brotzilla.neat.evolution;

import java.util.List;

import ch.brotzilla.neat.Debug;

public class StochasticUniformSelectionStrategy implements SelectionStrategy {

    private double computeTotalFitness(Population population) {
        double result = 0.0;
        for (final Individual individual : population) {
            result += individual.getFitness();
        }
        return result;
    }
    
    public StochasticUniformSelectionStrategy() {
    }

    public List<Individual> select(Population population, EvolutionConfig config) {
    	Debug.checkPopulationAndConfig(population, config, false);
    	final Objective obj = config.getObjective(0);
        final double totalFitness = computeTotalFitness(population);
        final double[] normalized = new double[population.size()];
        for (int i = 0; i < normalized.length; i++) {
        	normalized[i] = obj.normalize(population.get(i).getFitness(), totalFitness);
        }
        
    }
    
}

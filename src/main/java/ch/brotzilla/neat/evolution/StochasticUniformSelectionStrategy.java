package ch.brotzilla.neat.evolution;

import java.util.List;

import com.google.common.base.Preconditions;

public class StochasticUniformSelectionStrategy implements SelectionStrategy {

    private double computeTotalFitness(List<Individual> individuals) {
        double result = 0.0;
        for (final Individual individual : individuals) {
            result += individual.getObjective(0);
        }
        return result;
    }
    
    public StochasticUniformSelectionStrategy() {
    }

    public List<Individual> select(List<Individual> individuals, EvolutionConfig config) {
        Preconditions.checkNotNull(individuals, "The parameter 'individuals' must not be null");
        Preconditions.checkNotNull(config, "The parameter 'config' must not be null");
        final double totalFitness = computeTotalFitness(individuals);
    }

}

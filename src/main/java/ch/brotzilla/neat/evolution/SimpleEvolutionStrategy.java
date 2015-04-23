package ch.brotzilla.neat.evolution;

import java.util.Comparator;
import java.util.List;

import ch.brotzilla.neat.Debug;

import com.google.common.base.Preconditions;

public class SimpleEvolutionStrategy implements EvolutionStrategy {

    public SimpleEvolutionStrategy() {

    }

    public Population evolve(Population population, EvolutionConfig config) {
    	Debug.checkPopulationAndConfig(population, config, false);
    	
        population.sort(new IndividualComparator(config.getObjective(0)));
        
        final List<Individual> selected = config.getSelectionStrategy().select(population, config);
        Preconditions.checkState(selected != null, "The selection strategy must not return null");
        Preconditions.checkState(selected.size() == config.getSelectSize(), "The selection strategy has to select " + config.getSelectSize() + " individuals");
        
    }
    
    private class IndividualComparator implements Comparator<Individual> {

        private final Objective objective;
        
        public IndividualComparator(Objective objective) {
            Preconditions.checkNotNull(objective, "The parameter 'objective' must not be null");
            this.objective = objective;
        }
        
        public int compare(Individual a, Individual b) {
            Preconditions.checkNotNull(a, "The parameter 'a' must not be null");
            Preconditions.checkNotNull(b, "The parameter 'b' must not be null");
            return objective.compare(a.getObjective(0), b.getObjective(0));
        }
        
    }

}

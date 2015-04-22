package ch.brotzilla.neat.evolution;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class SimpleEvolutionStrategy implements EvolutionStrategy {

    public SimpleEvolutionStrategy() {

    }

    public Population evolve(Population population, EvolutionConfig config) {
        Preconditions.checkNotNull(population, "The parameter 'population' must not be null");
        Preconditions.checkArgument(population.getNumberOfObjectives() == 1, "Multiple objective populations are not supported");
        Preconditions.checkNotNull(config, "The parameter 'config' must not be null");
        Preconditions.checkArgument(config.getNumberOfObjectives() == 1, "Multiple objective configurations are not supported");
        
        final List<Individual> individuals = Lists.newArrayList();
        individuals.addAll(population.getIndividuals());
        Collections.sort(individuals, new IndividualComparator(config.getObjective(0)));
        
        final List<Individual> selected = config.getSelectionStrategy().select(individuals, config);
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

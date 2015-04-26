package ch.brotzilla.neat.evolution;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import ch.brotzilla.neat.genome.Genome;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class Population implements Iterable<Individual> {

    private final int numberOfObjectives;
    private final List<Individual> individuals, individualsWrapper;
    
    public Population(int numberOfObjectives) {
        Preconditions.checkArgument(numberOfObjectives > 0, "The parameter 'numberOfObjectives' has to be greater than zero");
        this.numberOfObjectives = numberOfObjectives;
        this.individuals = Lists.newArrayList();
        this.individualsWrapper = Collections.unmodifiableList(individuals);
    }
    
    public Population(Population source) {
        Preconditions.checkNotNull(source, "The parameter 'source' must not be null");
        numberOfObjectives = source.numberOfObjectives;
        individuals = Lists.newArrayList();
        individualsWrapper = Collections.unmodifiableList(individuals);
        for (final Individual individual : source.individuals) {
            individuals.add(individual.clone());
        }
    }

    public int getNumberOfObjectives() {
        return numberOfObjectives;
    }
    
    public List<Individual> getIndividuals() {
        return individualsWrapper;
    }
    
    public int size() {
        return individuals.size();
    }

    public Iterator<Individual> iterator() {
        return individualsWrapper.iterator();
    }
    
    public Individual get(int index) {
        return individuals.get(index);
    }
    
    public Individual add(Genome genome) {
        Preconditions.checkNotNull(genome, "The parameter 'genome' must not be null");
        final Individual individual = new Individual(getNumberOfObjectives(), genome);
        individuals.add(individual);
        return individual;
    }
    
    public Individual add(double[] objectives, Genome genome) {
        Preconditions.checkNotNull(objectives, "The parameter 'objectives' must not be null");
        Preconditions.checkArgument(objectives.length == getNumberOfObjectives(), "The length of the parameter 'objectives' has to be equal to " + getNumberOfObjectives());
        Preconditions.checkNotNull(genome, "The parameter 'genome' must not be null");
        final Individual individual = new Individual(objectives, genome);
        individuals.add(individual);
        return individual;
    }
    
    public Individual add(Individual individual, boolean copy) {
        Preconditions.checkNotNull(individual, "The parameter 'individual' must not be null");
        Preconditions.checkArgument(individual.getNumberOfObjectives() == getNumberOfObjectives(), "The number of objectives of the parameter 'individual' has to be equal to " + getNumberOfObjectives());
        final Individual result = copy ? individual.clone() : individual;
        individuals.add(result);
        return result;
    }
    
    public void sort(Comparator<Individual> comparator) {
    	Preconditions.checkNotNull(comparator, "The parameter 'comparator' must not be null");
    	Collections.sort(individuals, comparator);
    }
    
    @Override
    public Population clone() {
    	return new Population(this);
    }
    
}

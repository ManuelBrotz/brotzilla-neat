package ch.brotzilla.neat.evolution;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class Population {

	private final int numberOfObjectives;
	private final Individuals individuals;
	
	private Population(int numberOfObjectives, Individuals individuals) {
		Preconditions.checkArgument(numberOfObjectives > 0, "The parameter 'numberOfObjectives' has to be greater than zero");
		this.numberOfObjectives = numberOfObjectives;
		this.individuals = individuals;
	}

	public int getNumberOfObjectives() {
		return numberOfObjectives;
	}
	
	public Individuals getIndividuals() {
		return individuals;
	}
	
	public static class Builder {
	
		private final int numberOfObjectives;
		private List<Individual> individuals;
		
		public Builder(int numberOfObjectives) {
			Preconditions.checkArgument(numberOfObjectives > 0, "The parameter 'numberOfObjectives' has to be greater than zero");
			this.numberOfObjectives = numberOfObjectives;
			individuals = Lists.newArrayList();
		}
		
		public int getNumberOfObjectives() {
			return numberOfObjectives;
		}
		
		public Builder addIndividual(Individual individual, boolean copy) {
			Preconditions.checkNotNull(individual, "The parameter 'individual' must not be null");
			Preconditions.checkArgument(individual.getNumberOfObjectives() == numberOfObjectives, "The number of objectives of the parameter 'individual' has to be equal to " + numberOfObjectives);
			Preconditions.checkArgument(individual.getSpecies() > 0, "The species id of the parameter 'individual' has to be greater than zero");
			individuals.add(copy ? individual.clone() : individual);
			return this;
		}
		
		public Builder addIndividuals(List<Individual> individuals, boolean copyIndividuals) {
			Preconditions.checkNotNull(individuals, "The parameter 'individuals' must not be null");
			for (final Individual individual : individuals) {
				addIndividual(individual, copyIndividuals);
			}
			return this;
		}
		
		public Population build() {
			
		}
		
	}
	
}

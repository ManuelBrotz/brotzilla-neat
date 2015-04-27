package ch.brotzilla.neat.evolution;

import java.util.Arrays;

import com.google.common.base.Preconditions;

import ch.brotzilla.neat.genome.Genome;

public class Individual {

	private int species;
    private double[] objectives;
    private Genome genome;
    
    private Individual() {}
        
    public Individual(Individual source) {
        Preconditions.checkNotNull(source, "The parameter 'source' must not be null");
        species = source.species;
        objectives = Arrays.copyOf(source.objectives, source.objectives.length);
        genome = source.genome.clone();
    }
    
    public int getSpecies() {
    	return species;
    }
    
    public void setSpecies(int species) {
    	Preconditions.checkArgument(species > 0, "The parameter 'species' has to be greater than zero");
    	this.species = species;
    }
    
    public int getNumberOfObjectives() {
        return objectives.length;
    }
    
    public double[] getObjectives() {
        return objectives;
    }
    
    public double getObjective(int index) {
        return objectives[index];
    }
    
    public double getFitness() {
    	return objectives[0];
    }
    
    public Genome getGenome() {
        return genome;
    }

    @Override
    public Individual clone() {
        return new Individual(this);
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Individual) {
            final Individual individual = (Individual) other;
            if (!Arrays.equals(objectives, individual.objectives)) {
                return false;
            }
            if (!genome.equals(individual.genome)) {
                return false;
            }
            return true;
        }
        return false;
    }
    
    public static class Builder {
    	
    	private final Individual individual;
    	
    	public Builder() {
    		individual = new Individual();
    	}
    	
    	public Builder setSpecies(int species) {
    		Preconditions.checkArgument(species > 0, "The parameter 'species' has to be greater than zero");
    		individual.species = species;
    		return this;
    	}
    	
    	public Builder setNumberOfObjectives(int numberOfObjectives) {
    		Preconditions.checkArgument(numberOfObjectives > 0, "The parameter 'numberOfObjectives' has to be greater than zero");
    		individual.objectives = new double[numberOfObjectives];
    		return this;
    	}
    	
    	public Builder setObjectives(double[] objectives) {
    		Preconditions.checkNotNull(objectives, "The parameter 'objectives' must not be null");
    		Preconditions.checkArgument(objectives.length > 0, "The length of the parameter 'objectives' has to be greater than zero");
    		individual.objectives = Arrays.copyOf(objectives, objectives.length);
    		return this;
    	}
    	
    	public Builder setGenome(Genome genome) {
    		Preconditions.checkNotNull(genome, "The parameter 'genome' must not be null");
    		individual.genome = genome;
    		return this;
    	}
    	
    	public Individual build() {
    		Preconditions.checkArgument(individual.species >= 0, "The property 'species' has to be greater than or equal to zero");
    		Preconditions.checkNotNull(individual.objectives, "The property 'objectives' must not be null");
    		Preconditions.checkArgument(individual.objectives.length > 0, "The length of the property 'objectives' has to be greater than zero");
    		Preconditions.checkNotNull(individual.genome, "The property 'genome' must not be null");
    		return new Individual(individual);
    	}
    	
    }
    
}

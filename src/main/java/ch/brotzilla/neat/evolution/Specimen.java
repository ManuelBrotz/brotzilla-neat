package ch.brotzilla.neat.evolution;

import java.util.Arrays;

import com.google.common.base.Preconditions;

import ch.brotzilla.neat.genome.Genome;

public class Specimen {

	private int species;
    private double[] objectives;
    private Genome genome;
    
    private Specimen() {}
        
    public Specimen(Specimen source) {
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
    public Specimen clone() {
        return new Specimen(this);
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Specimen) {
            final Specimen specimen = (Specimen) other;
            if (!Arrays.equals(objectives, specimen.objectives)) {
                return false;
            }
            if (!genome.equals(specimen.genome)) {
                return false;
            }
            return true;
        }
        return false;
    }
    
    public static class Builder {
    	
    	private final Specimen specimen;
    	
    	public Builder() {
    		specimen = new Specimen();
    	}
    	
    	public Builder setSpecies(int species) {
    		Preconditions.checkArgument(species > 0, "The parameter 'species' has to be greater than zero");
    		specimen.species = species;
    		return this;
    	}
    	
    	public Builder setNumberOfObjectives(int numberOfObjectives) {
    		Preconditions.checkArgument(numberOfObjectives > 0, "The parameter 'numberOfObjectives' has to be greater than zero");
    		specimen.objectives = new double[numberOfObjectives];
    		return this;
    	}
    	
    	public Builder setObjectives(double[] objectives) {
    		Preconditions.checkNotNull(objectives, "The parameter 'objectives' must not be null");
    		Preconditions.checkArgument(objectives.length > 0, "The length of the parameter 'objectives' has to be greater than zero");
    		specimen.objectives = Arrays.copyOf(objectives, objectives.length);
    		return this;
    	}
    	
    	public Builder setGenome(Genome genome) {
    		Preconditions.checkNotNull(genome, "The parameter 'genome' must not be null");
    		specimen.genome = genome;
    		return this;
    	}
    	
    	public Specimen build() {
    		Preconditions.checkArgument(specimen.species >= 0, "The property 'species' has to be greater than or equal to zero");
    		Preconditions.checkNotNull(specimen.objectives, "The property 'objectives' must not be null");
    		Preconditions.checkArgument(specimen.objectives.length > 0, "The length of the property 'objectives' has to be greater than zero");
    		Preconditions.checkNotNull(specimen.genome, "The property 'genome' must not be null");
    		return new Specimen(specimen);
    	}
    	
    }
    
}

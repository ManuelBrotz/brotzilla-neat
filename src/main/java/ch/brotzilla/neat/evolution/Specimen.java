package ch.brotzilla.neat.evolution;

import java.util.Arrays;

import com.google.common.base.Preconditions;

import ch.brotzilla.neat.genome.Genome;

public class Specimen {

    private final double[] objectives;
    private final Genome genome;
    private int species;

    public Specimen(int numberOfObjectives, Genome genome) {
        Preconditions.checkArgument(numberOfObjectives > 0, "The parameter 'numberOfObjectives' has to be greater than zero");
        Preconditions.checkNotNull(genome, "The parameter 'genome' must not be null");
        objectives = new double[numberOfObjectives];
        this.genome = genome;
    }
    
    public Specimen(Objectives objectives, Genome genome) {
        Preconditions.checkNotNull(objectives, "The parameter 'objectives' must not be null");
        Preconditions.checkNotNull(genome, "The parameter 'genome' must not be null");
        this.objectives = new double[objectives.getNumberOfObjectives()];
        this.genome = genome;
    }
    
    public Specimen(double[] objectives, Genome genome) {
        Preconditions.checkNotNull(objectives, "The parameter 'objectives' must not be null");
        Preconditions.checkArgument(objectives.length > 0, "The length of the parameter 'objectives' has to be greater than zero");
        Preconditions.checkNotNull(genome, "The parameter 'genome' must not be null");
        this.objectives = Arrays.copyOf(objectives, objectives.length);
        this.genome = genome;
    }
    
    public Specimen(Specimen source) {
        Preconditions.checkNotNull(source, "The parameter 'source' must not be null");
        objectives = Arrays.copyOf(source.objectives, source.objectives.length);
        genome = source.genome.clone();
        species = source.species;
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
    
    public void setObjective(int index, double value) {
        objectives[index] = value;
    }
    
    public double getFitness() {
    	return objectives[0];
    }
    
    public Genome getGenome() {
        return genome;
    }

    public int getSpecies() {
        return species;
    }
    
    public void setSpecies(int species) {
        Preconditions.checkArgument(species > 0, "The parameter 'species' has to be greater than zero");
        this.species = species;
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
            if (species != specimen.species) {
                return false;
            }
            return true;
        }
        return false;
    }
    
}

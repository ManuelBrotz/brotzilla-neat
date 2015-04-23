package ch.brotzilla.neat.evolution;

import java.util.Arrays;

import com.google.common.base.Preconditions;

import ch.brotzilla.neat.genome.Genome;

public class Individual {

    private final double[] objectives;
    private final Genome genome;
    
    public Individual(int objectives, Genome genome) {
        Preconditions.checkArgument(objectives > 0, "The parameter 'objectives' has to be greater than zero");
        Preconditions.checkNotNull(genome, "The parameter 'genome' must not be null");
        this.objectives = new double[objectives];
        this.genome = genome;
    }
    
    public Individual(double[] objectives, Genome genome) {
        Preconditions.checkNotNull(objectives, "The parameter 'objectives' must not be null");
        Preconditions.checkNotNull(genome, "The parameter 'genome' must not be null");
        this.objectives = Arrays.copyOf(objectives, objectives.length);
        this.genome = genome;
    }
    
    public Individual(Individual source) {
        Preconditions.checkNotNull(source, "The parameter 'source' must not be null");
        objectives = Arrays.copyOf(source.objectives, source.objectives.length);
        genome = source.genome.clone();
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
}

package ch.brotzilla.neat.evolution.speciation;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import ch.brotzilla.neat.evolution.Rng;
import ch.brotzilla.neat.evolution.Speciation;
import ch.brotzilla.neat.evolution.SpeciationStrategy;
import ch.brotzilla.neat.evolution.Specimen;
import ch.brotzilla.neat.genome.Genome;

public class KMeansSpeciationStrategy implements SpeciationStrategy {

    private final int numberOfSpecies;
    private final Rng rng;
    private final Entry[] entries;
    
    private double distance(Genome a, Genome b) {
        
    }
    
    public KMeansSpeciationStrategy(int numberOfSpecies) {
        Preconditions.checkArgument(numberOfSpecies > 1, "The parameter 'numberOfSpecies' has to be greater than 1");
        this.numberOfSpecies = numberOfSpecies;
        entries = new Entry[numberOfSpecies];
    }
    
    public int getNumberOfSpecies() {
        return numberOfSpecies;
    }

    public Speciation speciate(List<Specimen> population) {
        if (entries[0] == null) {
            for (int i = 0; i < numberOfSpecies; i++) {
                entries[i] = new Entry(i+1);
            }
            for (int i = 0; i < population.size(); i++) {
                entries[i % numberOfSpecies].add(population.get(i));
            }
        } else {
            
        }
    }

    private static class Entry {
        
        public final int id;
        public final List<Specimen> specimen;
        public final CentroidCalculator centroidCalculator;
        
        public Entry(int id) {
            Preconditions.checkArgument(id > 0, "The parameter 'id' has to be greater than 1");
            this.id = id;
            specimen = Lists.newArrayList();
            centroidCalculator = new CentroidCalculator();
        }
        
        public void add(Specimen specimen) {
            
        }
        
    }
    
}

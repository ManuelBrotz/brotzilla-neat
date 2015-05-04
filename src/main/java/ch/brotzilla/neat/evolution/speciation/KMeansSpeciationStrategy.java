package ch.brotzilla.neat.evolution.speciation;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import ch.brotzilla.neat.evolution.Speciation;
import ch.brotzilla.neat.evolution.SpeciationStrategy;
import ch.brotzilla.neat.evolution.Species;
import ch.brotzilla.neat.evolution.Specimen;

public class KMeansSpeciationStrategy implements SpeciationStrategy {

    private final int numberOfSpecies, maxIterations, minSpeciesSize;
    private Entries entries;
    
    public KMeansSpeciationStrategy(int numberOfSpecies, int maxIterations, int minSpeciesSize) {
        Preconditions.checkArgument(numberOfSpecies > 1, "The parameter 'numberOfSpecies' has to be greater than 1");
        Preconditions.checkArgument(maxIterations > 0, "The parameter 'maxIterations' has to be greater than zero");
        Preconditions.checkArgument(minSpeciesSize >= 0, "The parameter 'minSpeciesSize' has to be greater than or equal to zero");
        this.numberOfSpecies = numberOfSpecies;
        this.maxIterations = maxIterations;
        this.minSpeciesSize = minSpeciesSize;
    }
    
    public int getNumberOfSpecies() {
        return numberOfSpecies;
    }
    
    public int getMaxIterations() {
        return maxIterations;
    }
    
    public int getMinSpeciesSize() {
        return minSpeciesSize;
    }

    public Speciation speciate(List<Specimen> population) {
        if (entries == null) {
            entries = new Entries(numberOfSpecies, minSpeciesSize);
            entries.initializeSpeciation(population);
        } else {
            entries = entries.resumeSpeciation(population);
        }
        int iterations = 0;
        do {
            entries = entries.performSpeciationIteration();
            if (minSpeciesSize > 0 && entries.handleEmptySpecies()) {
                iterations = 0;
            }
            ++iterations;
        } while (entries.changes > (population.size() / 20) && iterations < maxIterations);
        return speciate();
    }

    private Speciation speciate() {
        final List<Species> speciation = Lists.newArrayList();
        for (final Entry e : entries.entries) {
            speciation.add(new Species(e.id, e.list, false));
        }
        entries.clear();
        return new Speciation(speciation, false);
    }
    
    private static class Entries {
        
        public final int numberOfSpecies, minSpeciesSize;
        public final Entry[] entries;
        public int changes;
        
        public Entries(int numberOfSpecies, int minSpeciesSize) {
            Preconditions.checkArgument(numberOfSpecies > 1, "The parameter 'numberOfSpecies' has to be greater than 1");
            Preconditions.checkArgument(minSpeciesSize >= 0, "The parameter 'minSpeciesSize' has to be greater than or equal to zero");
            this.numberOfSpecies = numberOfSpecies;
            this.minSpeciesSize = minSpeciesSize;
            entries = new Entry[numberOfSpecies];
            for (int i = 0; i < numberOfSpecies; i++) {
                entries[i] = new Entry(i+1);
            }
        }
        
        public int findNearest(Specimen specimen) {
            int result = 0;
            double nearest = entries[0].centroid.distance(specimen.getGenome());
            for (int i = 1; i < numberOfSpecies; i++) {
                final double distance = entries[i].centroid.distance(specimen.getGenome());
                if (distance < nearest) {
                    result = i;
                    nearest = distance;
                }
            }
            return result;
        }
        
        public void initializeSpeciation(List<Specimen> population) {
            Preconditions.checkNotNull(population, "The parameter 'population' must not be null");
            for (int i = 0; i < population.size(); i++) {
                entries[i % numberOfSpecies].add(population.get(i));
            }
            computeCentroids();
        }

        public Entries resumeSpeciation(List<Specimen> population) {
            Preconditions.checkNotNull(population, "The parameter 'population' must not be null");
            final Entries result = new Entries(numberOfSpecies, minSpeciesSize);
            for (int i = 0; i < population.size(); i++) {
                final Specimen specimen = population.get(i);
                final int nearest = findNearest(specimen);
                result.entries[nearest].add(specimen);
            }
            result.computeCentroids();
            return result;
        }
        
        private Entries performSpeciationIteration() {
            Preconditions.checkNotNull(entries, "The parameter 'entries' must not be null");
            final Entries result = new Entries(numberOfSpecies, minSpeciesSize);
            for (int iSource = 0; iSource < numberOfSpecies; iSource++) {
                final Entry source = entries[iSource];
                for (int iSpecimen = 0; iSpecimen < source.list.size(); iSpecimen++) {
                    final Specimen specimen = source.list.get(iSpecimen);
                    final int iNearest = findNearest(specimen);
                    result.entries[iNearest].add(specimen);
                    if (iNearest != iSource) {
                        result.changes++;
                    }
                }
            }
            result.computeCentroids();
            return result;
        }
        
        private int findEmptySpecies() {
            for (int i = 0; i < numberOfSpecies; i++) {
                if (entries[i].list.size() < minSpeciesSize) {
                    return i;
                }
            }
            return -1;
        }
        
        private int findLargestSpecies() {
            int max = entries[0].list.size(), index = 0;
            for (int i = 1; i < numberOfSpecies; i++) {
                if (entries[i].list.size() > max) {
                    max = entries[i].list.size();
                    index = i;
                }
            }
            return index;
        }
        
        private boolean handleEmptySpecies() {
            final int emptyIndex = findEmptySpecies();
            if (emptyIndex >= 0) {
                final int largestIndex = findLargestSpecies();
                final Entry largestSpecies = entries[largestIndex];
                final Entry a = new Entry(emptyIndex + 1), b = new Entry(largestIndex + 1);
                for (int i = 0; i < largestSpecies.list.size(); i++) {
                    final Specimen specimen = largestSpecies.list.get(i);
                    if (i % 2 == 0) {
                        a.add(specimen);
                    } else {
                        b.add(specimen);
                    }
                }
                a.compute();
                b.compute();
                changes += largestSpecies.list.size();
                entries[emptyIndex] = a;
                entries[largestIndex] = b;
                handleEmptySpecies();
                return true;
            }
            return false;
        }
        
        public void computeCentroids() {
            for (final Entry e : entries) {
                e.compute();
            }
        }

        public void clear() {
            for (final Entry e : entries) {
                e.clear();
            }
        }
        
    }
    
    private static class Entry {
        
        public final int id;
        public final List<Specimen> list;
        public final CentroidCalculator calculator;
        public Centroid centroid;
        
        public Entry(int id) {
            Preconditions.checkArgument(id > 0, "The parameter 'id' has to be greater than zero");
            this.id = id;
            list = Lists.newArrayList();
            calculator = new CentroidCalculator();
        }
        
        public void add(Specimen specimen) {
            Preconditions.checkNotNull(specimen, "The parameter 'specimen' must not be null");
            specimen.setSpecies(id);
            list.add(specimen);
            calculator.add(specimen.getGenome());
        }
        
        public void compute() {
            centroid = calculator.compute();
        }
        
        public void clear() {
            list.clear();
            calculator.clear();
        }
        
    }
    
}

package ch.brotzilla.neat.evolution.speciation;

import gnu.trove.impl.Constants;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import ch.brotzilla.neat.evolution.Speciation;
import ch.brotzilla.neat.evolution.SpeciationStrategy;
import ch.brotzilla.neat.evolution.Species;
import ch.brotzilla.neat.evolution.Specimen;

public class KMeansSpeciationStrategy implements SpeciationStrategy {

    private final int maxIterations;
    private final boolean reuseExistingSpecies;
    private final Entries entries;
    private boolean initialized = false;
    
    public KMeansSpeciationStrategy(int numberOfSpecies, int minSpeciesSize, int maxIterations, boolean reuseExistingSpecies) {
        Preconditions.checkArgument(numberOfSpecies > 1, "The parameter 'numberOfSpecies' has to be greater than 1");
        Preconditions.checkArgument(minSpeciesSize >= 0, "The parameter 'minSpeciesSize' has to be greater than or equal to zero");
        Preconditions.checkArgument(maxIterations > 0, "The parameter 'maxIterations' has to be greater than zero");
        this.maxIterations = maxIterations;
        this.reuseExistingSpecies = reuseExistingSpecies;
        entries = new Entries(numberOfSpecies, minSpeciesSize, reuseExistingSpecies);
    }
    
    public int getNumberOfSpecies() {
        return entries.numberOfSpecies;
    }
    
    public int getMaxIterations() {
        return maxIterations;
    }
    
    public int getMinSpeciesSize() {
        return entries.minSpeciesSize;
    }
    
    public boolean getReuseExistingSpecies() {
        return reuseExistingSpecies;
    }

    public Speciation speciate(List<Specimen> population) {
        if (!initialized) {
            entries.initializeSpeciation(population);
            initialized = true;
        } else {
            entries.resumeSpeciation(population);
        }
        int iterations = 0;
        do {
            entries.performSpeciationIteration(population);
            if (entries.minSpeciesSize > 0 && entries.handleEmptyEntries()) {
                iterations = 0;
            }
            ++iterations;
        } while (entries.changes > (population.size() / 20) && iterations < maxIterations);
        return speciate();
    }

    private Speciation speciate() {
        final List<Species> speciation = Lists.newArrayList();
        for (final Entry e : entries) {
            speciation.add(new Species(e.id, e.list, false));
        }
        return new Speciation(speciation, false);
    }
    
    private static class Entries implements Iterable<Entry> {
        
        public final int numberOfSpecies, minSpeciesSize;
        public final TIntObjectMap<Entry> entries;
        public final boolean reuseExistingSpecies;
        public int nextId = 1, changes;
        
        public Entries(int numberOfSpecies, int minSpeciesSize, boolean reuseExistingSpecies) {
            Preconditions.checkArgument(numberOfSpecies > 1, "The parameter 'numberOfSpecies' has to be greater than 1");
            Preconditions.checkArgument(minSpeciesSize >= 0, "The parameter 'minSpeciesSize' has to be greater than or equal to zero");
            this.numberOfSpecies = numberOfSpecies;
            this.minSpeciesSize = minSpeciesSize;
            this.reuseExistingSpecies = reuseExistingSpecies;
            entries = new TIntObjectHashMap<Entry>(Constants.DEFAULT_CAPACITY, Constants.DEFAULT_LOAD_FACTOR, 0);
            for (int i = 0; i < numberOfSpecies; i++) {
                final int nextId = nextId();
                entries.put(nextId, new Entry(nextId));
            }
        }
        
        public int nextId() {
            return nextId++;
        }
        
        public int findNearest(Specimen specimen) {
            Preconditions.checkNotNull(specimen, "The parameter 'specimen' must not be null");
            Entry result = null;
            double nearest = 0;
            for (final Entry e : this) {
                final double distance = e.getCentroid().distance(specimen.getGenome());
                if (result == null || distance < nearest) {
                    result = e;
                    nearest = distance;
                }
            }
            return Preconditions.checkNotNull(result, "Internal Error: No nearest entry found").id;
        }
        
        public int findEmptyId() {
            for (final Entry e : this) {
                if (e.list.size() < minSpeciesSize) {
                    return e.id;
                }
            }
            return 0;
        }
        
        public Entry findLargestEntry() {
            Entry result = null;
            int max = 0;
            for (final Entry e : this) {
                final int size = e.list.size();
                if (result == null || size > max) {
                    result = e;
                    max = size;
                }
            }
            return Preconditions.checkNotNull(result, "Internal Error: No largest species found");
        }
        
        public void initializeSpeciation(List<Specimen> population) {
            Preconditions.checkNotNull(population, "The parameter 'population' must not be null");
            int i = 0;
            while (i < population.size()) {
                for (final Entry e : this) {
                    e.add(population.get(i++));
                    if (i >= population.size()) break;
                }
            }
            computeCentroids();
        }

        public void resumeSpeciation(List<Specimen> population) {
            Preconditions.checkNotNull(population, "The parameter 'population' must not be null");
            clear();
            for (final Specimen specimen : population) {
                final int nearest = findNearest(specimen);
                Preconditions.checkNotNull(entries.get(nearest), "Internal Error: Entry not found: " + nearest).add(specimen);
            }
            computeCentroids();
        }
        
        public void performSpeciationIteration(List<Specimen> population) {
            Preconditions.checkNotNull(population, "The parameter 'population' must not be null");
            clear();
            for (final Specimen specimen : population) {
                final int nearest = findNearest(specimen);
                if (nearest != specimen.getSpecies()) {
                    changes++;
                }
                Preconditions.checkNotNull(entries.get(nearest), "Internal Error: Entry not found: " + nearest).add(specimen);
            }
            computeCentroids();
        }
        
        public boolean handleEmptyEntries() {
            final int emptyId = findEmptyId();
            if (emptyId > 0) {
                final Entry largestEntry = findLargestEntry();
                final Entry largestEntryReplacement = new Entry(largestEntry.id);
                final Entry newEntry = new Entry(reuseExistingSpecies ? emptyId : nextId());
                for (int i = 0; i < largestEntry.list.size(); i++) {
                    final Specimen specimen = largestEntry.list.get(i);
                    if (i % 2 == 0) {
                        largestEntryReplacement.add(specimen);
                    } else {
                        newEntry.add(specimen);
                    }
                }
                largestEntryReplacement.compute();
                newEntry.compute();
                changes += newEntry.list.size();
                entries.put(largestEntry.id, largestEntryReplacement);
                if (reuseExistingSpecies) {
                    entries.put(emptyId, newEntry);
                } else {
                    entries.remove(emptyId);
                    entries.put(newEntry.id, newEntry);
                }
                handleEmptyEntries();
                return true;
            }
            return false;
        }
        
        public void computeCentroids() {
            for (final Entry e : this) {
                e.compute();
            }
        }

        public void clear() {
            changes = 0;
            for (final Entry e : this) {
                e.clear();
            }
        }

        public Iterator<Entry> iterator() {
            Preconditions.checkState(entries != null, "Internal Error: No entries available");
            Preconditions.checkState(entries.size() == numberOfSpecies, "Internal Error: " + entries.size() + " entries found (" + numberOfSpecies + " expected)");
            return entries.valueCollection().iterator();
        }
        
    }
    
    private static class Entry {
        
        public final int id;
        public final List<Specimen> list;
        public final CentroidCalculator calculator;
        public Centroid centroid_;
        
        public Entry(int id) {
            Preconditions.checkArgument(id > 0, "The parameter 'id' has to be greater than zero");
            this.id = id;
            list = Lists.newArrayList();
            calculator = new CentroidCalculator();
        }
        
        public Centroid getCentroid() {
            return Preconditions.checkNotNull(centroid_, "Internal Error: Centroid not available");
        }
        
        public void add(Specimen specimen) {
            Preconditions.checkNotNull(specimen, "The parameter 'specimen' must not be null");
            specimen.setSpecies(id);
            list.add(specimen);
            calculator.add(specimen.getGenome());
        }
        
        public void compute() {
            centroid_ = calculator.compute();
        }
        
        public void clear() {
            list.clear();
            calculator.clear();
        }
        
    }
    
}

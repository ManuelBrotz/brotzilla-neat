package ch.brotzilla.neat.evolution;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class Species implements Iterable<Specimen> {

    private final int id;
    private final List<Specimen> specimens, specimensWrapper;
    
    public Species(int id, List<Specimen> specimens, boolean copy) {
        Preconditions.checkArgument(id > 0, "The parameter 'id' has to be greater than zero");
        Preconditions.checkNotNull(specimens, "The parameter 'specimens' must not be null");
        this.id = id;
        this.specimens = Lists.newArrayListWithCapacity(specimens.size());
        this.specimensWrapper = Collections.unmodifiableList(specimens);
        for (final Specimen specimen : specimens) {
            Preconditions.checkArgument(specimen.getSpecies() == id, "The parameter 'specimens' may only contain specimens with species id " + id);
            this.specimens.add(copy ? specimen.clone() : specimen);
        }
    }
    
    public Species(Species source) {
        Preconditions.checkNotNull(source, "The parameter 'source' must not be null");
        id = source.id;
        specimens = Lists.newArrayListWithCapacity(source.size());
        specimensWrapper = Collections.unmodifiableList(specimens);
        for (final Specimen specimen : source) {
            specimens.add(specimen.clone());
        }
    }

    public int getId() {
        return id;
    }
    
    public List<Specimen> getSpecimens() {
        return specimensWrapper;
    }
    
    public int size() {
        return specimens.size();
    }
    
    public Specimen getSpecimen(int index) {
        return specimens.get(index);
    }

    public void sort(Comparator<Specimen> comparator) {
        Preconditions.checkNotNull(comparator, "The parameter 'comparator' must not be null");
        Collections.sort(specimens, comparator);
    }

    public Iterator<Specimen> iterator() {
        return specimensWrapper.iterator();
    }
    
    @Override
    public Species clone() {
        return new Species(this);
    }
    
}

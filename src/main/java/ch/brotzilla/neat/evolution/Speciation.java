package ch.brotzilla.neat.evolution;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class Speciation implements Iterable<Species> {

    private static final SpeciesComparator speciesComparator = new SpeciesComparator();
    
	private final List<Species> list, listWrapper;
	private final TIntObjectMap<Species> map;

	private void add(List<Species> speciesList, boolean copy) {
	    for (Species species : speciesList) {
	        Preconditions.checkState(!map.containsKey(species.getId()), "Only unique species ids are supported (duplicate id: " + species.getId() + ")");
	        if (copy) {
	            species = species.clone();
	        }
	        map.put(species.getId(), species);
	        list.add(species);
	    }
	    Collections.sort(list, speciesComparator);
	}
	
	public Speciation(List<Species> speciesList, boolean copy) {
        Preconditions.checkNotNull(speciesList, "The parameter 'speciesList' must not be null");
        list = Lists.newArrayList();
        listWrapper = Collections.unmodifiableList(list);
        map = new TIntObjectHashMap<Species>();
        add(speciesList, copy);
	}
	
	public Speciation(Speciation source) {
	    Preconditions.checkNotNull(source, "The parameter 'source' must not be null");
        list = Lists.newArrayList();
        listWrapper = Collections.unmodifiableList(list);
        map = new TIntObjectHashMap<Species>();
        add(source.list, true);
	}

	public List<Species> getSpecies() {
	    return listWrapper;
	}
	
	public Species getSpecies(int index) {
	    return list.get(index);
	}
	
	public Species getSpeciesById(int id) {
	    return map.get(id);
	}
	
	public void sortAllSpecies(Comparator<Specimen> comparator) {
	    Preconditions.checkNotNull(comparator, "The parameter 'comparator' must not be null");
	    for (final Species species : list) {
	        species.sort(comparator);
	    }
	}

    public Iterator<Species> iterator() {
        return listWrapper.iterator();
    }

    @Override
    public Speciation clone() {
        return new Speciation(this);
    }
    
	private static class SpeciesComparator implements Comparator<Species> {

        public int compare(Species a, Species b) {
            Preconditions.checkNotNull(a, "The parameter 'a' must not be null");
            Preconditions.checkNotNull(b, "The parameter 'b' must not be null");
            if (a.getId() < b.getId()) return -1;
            if (a.getId() > b.getId()) return 1;
            return 0;
        }
	    
	}
	
}

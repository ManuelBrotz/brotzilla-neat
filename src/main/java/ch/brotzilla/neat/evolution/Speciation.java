package ch.brotzilla.neat.evolution;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class Speciation {

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
	    // TODO Sort the species list !!
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

	public List<Species> getList() {
	    return listWrapper;
	}
	
	public Species getSpecies(int index) {
	    return list.get(index);
	}
	
	public Species getSpeciesById(int id) {
	    return map.get(id);
	}
	
}

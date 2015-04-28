package ch.brotzilla.neat.evolution;

import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class Population {

	private final int numberOfObjectives;
	private final List<Specimen> specimens, specimensWrapper;
	private final List<Species> speciation, speciationWrapper;
	private final TIntObjectMap<Species> speciationMap;

	private void add(List<Species> speciesList, boolean copy) {
	    for (Species species : speciesList) {
	        Preconditions.checkState(!speciationMap.containsKey(species.getId()), "Only unique species ids are supported (duplicate id: " + species.getId() + ")");
	        if (copy) {
	            species = species.clone();
	        }
	        speciationMap.put(species.getId(), species);
	        speciation.add(species);
	        for (Specimen specimen : species) {
	            Preconditions.checkArgument(specimen.getNumberOfObjectives() == getNumberOfObjectives(), "Only specimens with " + getNumberOfObjectives() + " objective(s) are supported");
	            specimens.add(specimen);
	        }
	    }
	}
	
	public Population(int numberOfObjectives, List<Species> speciesList, boolean copy) {
        Preconditions.checkArgument(numberOfObjectives > 0, "The parameter 'numberOfObjectives' has to be greater than zero");
        Preconditions.checkNotNull(speciesList, "The parameter 'speciesList' must not be null");
        this.numberOfObjectives = numberOfObjectives;
        specimens = Lists.newArrayList();
        specimensWrapper = Collections.unmodifiableList(specimens);
        speciation = Lists.newArrayList();
        speciationWrapper = Collections.unmodifiableList(speciation);
        speciationMap = new TIntObjectHashMap<Species>();
        add(speciesList, copy);
	}
	
	public Population(Population source) {
	    Preconditions.checkNotNull(source, "The parameter 'source' must not be null");
        numberOfObjectives = source.numberOfObjectives;
        specimens = Lists.newArrayList();
        specimensWrapper = Collections.unmodifiableList(specimens);
        speciation = Lists.newArrayList();
        speciationWrapper = Collections.unmodifiableList(speciation);
        speciationMap = new TIntObjectHashMap<Species>();
        add(source.speciation, true);
	}

	public int getNumberOfObjectives() {
		return numberOfObjectives;
	}
	
	public List<Specimen> getSpecimens() {
		return specimensWrapper;
	}
	
	public List<Species> getSpeciation() {
	    return speciationWrapper;
	}
	
	public Specimen getSpecimen(int index) {
	    return specimens.get(index);
	}
	
	public Species getSpecies(int index) {
	    return speciation.get(index);
	}
	
	public Species getSpeciesById(int id) {
	    return speciationMap.get(id);
	}
	
}

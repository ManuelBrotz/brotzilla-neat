package ch.brotzilla.neat.evolution;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class Individuals implements Iterable<Individual> {

	private final List<Individual> individuals, individualsWrapper;

	public Individuals(List<Individual> individuals) {
		Preconditions.checkNotNull(individuals, "The parameter 'individuals' must not be null");
		this.individuals = Lists.newArrayListWithExpectedSize(individuals.size());
		this.individuals.addAll(individuals);
		individualsWrapper = Collections.unmodifiableList(individuals);
	}
	
	public List<Individual> getList() {
		return individualsWrapper;
	}
	
	public int size() {
		return individuals.size();
	}
	
	public Individual get(int index) {
		return individuals.get(index);
	}
	
	public Iterator<Individual> iterator() {
		return individualsWrapper.iterator();
	}
	
}
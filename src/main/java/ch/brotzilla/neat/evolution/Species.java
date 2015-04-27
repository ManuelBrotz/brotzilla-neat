package ch.brotzilla.neat.evolution;

import com.google.common.base.Preconditions;

public class Species extends Individuals {

	private final int id;
	
	public Species(int id, int numberOfObjectives) {
		super(numberOfObjectives);
		Preconditions.checkArgument(id > 0, "The parameter 'id' has to be greater than zero");
		this.id = id;
	}

	public Species(Species source) {
		super(source);
		id = source.id;
	}
	
	public int getId() {
		return id;
	}

	@Override
	public Species clone() {
		return new Species(this);
	}

}

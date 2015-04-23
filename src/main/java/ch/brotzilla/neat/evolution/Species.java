package ch.brotzilla.neat.evolution;

public class Species extends Individuals {
	
	public Species(int numberOfObjectives) {
		super(numberOfObjectives);
	}
	
	public Species(Species source) {
		super(source);
	}
	
	@Override
	public Species clone() {
		return new Species(this);
	}
	
}

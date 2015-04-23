package ch.brotzilla.neat.evolution;

public class Population extends Individuals {

    public Population(int numberOfObjectives) {
    	super(numberOfObjectives);
    }
    
    public Population(Population source) {
        super(source);
    }

    @Override
    public Population clone() {
        return new Population(this);
    }
    
}

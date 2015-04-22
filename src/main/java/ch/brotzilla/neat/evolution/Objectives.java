package ch.brotzilla.neat.evolution;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.google.common.base.Preconditions;

public class Objectives implements Iterable<Objective> {

    private Objective[] objectives;
    
    public Objectives(Objective... objectives) {
        Preconditions.checkNotNull(objectives, "The parameter 'objectives' must not be null");
        Preconditions.checkArgument(objectives.length > 0, "The length of the parameter 'objectives' has to be greater than zero");
        for (int i = 0; i < objectives.length; i++) {
            Preconditions.checkNotNull(objectives[i], "The parameter 'objectives[i]' must not be null");
        }
        this.objectives = Arrays.copyOf(objectives, objectives.length);
    }
    
    public int getNumberOfObjectives() {
        return objectives.length;
    }
    
    public Objective get(int index) {
        return objectives[index];
    }

    public Iterator<Objective> iterator() {
        return new ObjectivesIterator(objectives);
    }
    
    private static class ObjectivesIterator implements Iterator<Objective> {

        private final Objective[] objectives;
        private int current = -1;
        
        public ObjectivesIterator(Objective[] objectives) {
            this.objectives = objectives;
        }
        
        public boolean hasNext() {
            return current + 1 < objectives.length;
        }

        public Objective next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return objectives[++current];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
        
    }

}

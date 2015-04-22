package ch.brotzilla.neat.evolution;

import com.google.common.base.Preconditions;

public enum Objective {

    Minimize() {

        @Override
        public int compare(double a, double b) {
            Preconditions.checkArgument(!Double.isNaN(a), "The parameter 'a' must not be NaN");
            Preconditions.checkArgument(!Double.isNaN(b), "The parameter 'b' must not be NaN");
            Preconditions.checkArgument(!Double.isInfinite(a), "The parameter 'a' must not be infinite");
            Preconditions.checkArgument(!Double.isInfinite(b), "The parameter 'b' must not be infinite");
            if (a < b) {
                return -1;
            }
            if (a > b) {
                return 1;
            }
            return 0;
        }

        @Override
        public boolean isBetter(double a, double b) {
            return a < b;
        }

        @Override
        public double normalize(double individual, double total) {
            Preconditions.checkArgument(individual > 0, "The parameter 'individual' has to be greater than zero");
            Preconditions.checkArgument(total > 0, "The parameter 'total' has to be greater than zero");
            Preconditions.checkArgument(total > individual, "The parameter 'total' has to be greater than the parameter 'individual'");
            return 1.0 - (individual / total);
        }
        
    }, 
    
    Maximize() {

        @Override
        public int compare(double a, double b) {
            Preconditions.checkArgument(!Double.isNaN(a), "The parameter 'a' must not be NaN");
            Preconditions.checkArgument(!Double.isNaN(b), "The parameter 'b' must not be NaN");
            Preconditions.checkArgument(!Double.isInfinite(a), "The parameter 'a' must not be infinite");
            Preconditions.checkArgument(!Double.isInfinite(b), "The parameter 'b' must not be infinite");
            if (a < b) {
                return 1;
            }
            if (a > b) {
                return -1;
            }
            return 0;
        }

        @Override
        public boolean isBetter(double a, double b) {
            return a > b;
        }

        @Override
        public double normalize(double individual, double total) {
            Preconditions.checkArgument(individual > 0, "The parameter 'individual' has to be greater than zero");
            Preconditions.checkArgument(total > 0, "The parameter 'total' has to be greater than zero");
            Preconditions.checkArgument(total > individual, "The parameter 'total' has to be greater than the parameter 'individual'");
            return individual / total;
        }
        
    };
    
    private Objective() {}

    public abstract int compare(double a, double b);
    
    public abstract boolean isBetter(double a, double b);

    public abstract double normalize(double individual, double total);
    
}

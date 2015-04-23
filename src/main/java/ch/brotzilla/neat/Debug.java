package ch.brotzilla.neat;

import ch.brotzilla.neat.evolution.EvolutionConfig;
import ch.brotzilla.neat.evolution.Population;

import com.google.common.base.Preconditions;

public class Debug {

    private Debug() {}

    /**
     * Enables additional integrity checks. <br>
     * Such checks are only there for debugging and are not part of the normal functionality of a class or method. 
     */
    public static final boolean EnableIntegrityChecks = true;
    
    public static final void checkPopulationAndConfig(Population population, EvolutionConfig config, boolean checkMultipleObjectives) {
		Preconditions.checkNotNull(population, "The parameter 'population' must not be null");
		Preconditions.checkNotNull(config, "The parameter 'config' must not be null");
    	if (checkMultipleObjectives) {
    		Preconditions.checkArgument(population.getNumberOfObjectives() > 1, "The number of objectives of the parameter 'population' has to be greater than 1");
    		Preconditions.checkArgument(population.getNumberOfObjectives() == config.getNumberOfObjectives(), "The number of objectives of the parameter 'population' has to be equal to the number of objectives of the parameter 'config'");
    	} else {
    		Preconditions.checkArgument(population.getNumberOfObjectives() == 1, "The number of objectives of the parameter 'population' has to be equal to 1");
    		Preconditions.checkArgument(config.getNumberOfObjectives() == 1, "The number of objectives of the parameter 'config' has to be equal to 1");
    	}
    }
    
}

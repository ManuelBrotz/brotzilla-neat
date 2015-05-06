package ch.brotzilla.cppns.genomes;

import ch.brotzilla.neat.evolution.Rng;
import ch.brotzilla.neat.math.ActivationFunction;

public interface ActivationFunctionProvider {

    ActivationFunction[] provide(int count, Rng rng);
    
    ActivationFunction provide(Rng rng);
    
}

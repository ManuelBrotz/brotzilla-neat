package ch.brotzilla.neat.evolution;

import java.util.List;

public interface SpeciationStrategy {

    Speciation speciate(Speciation previousPopulation, List<Specimen> specimen);
    
}

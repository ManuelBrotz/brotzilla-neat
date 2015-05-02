package ch.brotzilla.neat.evolution;

import java.util.List;

public interface SpeciationStrategy {

    Speciation speciate(List<Specimen> population);
    
}

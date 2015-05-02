package ch.brotzilla.neat.evolution;

import java.util.List;

public interface PopulationProvider {

    List<Specimen> providePopulation(EvolutionConfig config);

}

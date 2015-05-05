package ch.brotzilla.neat.evolution.speciation;

import java.util.List;

import com.google.common.collect.Lists;

import ch.brotzilla.neat.evolution.Speciation;
import ch.brotzilla.neat.evolution.SpeciationStrategy;
import ch.brotzilla.neat.evolution.Species;
import ch.brotzilla.neat.evolution.Specimen;

public class NoSpeciationStrategy implements SpeciationStrategy {

    public Speciation speciate(List<Specimen> population) {
        for (final Specimen specimen : population) {
            specimen.setSpecies(1);
        }
        final List<Species> list = Lists.newArrayList();
        list.add(new Species(1, population, false));
        return new Speciation(list, false);
    }

}

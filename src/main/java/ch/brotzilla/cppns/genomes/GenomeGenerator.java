package ch.brotzilla.cppns.genomes;

import ch.brotzilla.cppns.ImageType;
import ch.brotzilla.neat.evolution.Rng;
import ch.brotzilla.neat.genome.Genome;

public interface GenomeGenerator {

    Genome generate(ImageType imageType, Rng rng);
    
}

package ch.brotzilla.patterns;

import ch.brotzilla.neat.genome.Genome;

public interface GenomeGenerator {

    Genome generate(ImageType imageType);
    
}

package ch.brotzilla.cppns;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import ch.brotzilla.cppns.images.ImageGenerator;
import ch.brotzilla.cppns.images.ImageType;
import ch.brotzilla.cppns.patterns.PatternGenerator;
import ch.brotzilla.cppns.patterns.BasicPatternGenerator;
import ch.brotzilla.neat.evolution.Rng;
import ch.brotzilla.neat.expression.NEATGenomeExpressor;
import ch.brotzilla.neat.genome.Genome;
import ch.brotzilla.neat.genome.Genomes;
import ch.brotzilla.neat.history.HistoryList;
import ch.brotzilla.neat.math.ActivationFunction;
import ch.brotzilla.neat.math.ExtendedDecliningCosFunction;
import ch.brotzilla.neat.math.ExtendedPeriodicCosFunction;
import ch.brotzilla.neat.math.ExtendedPeriodicSawtoothFunction;
import ch.brotzilla.neat.math.ExtendedPeriodicTriangleFunction;
import ch.brotzilla.neat.math.ExtendedTanhFunction;
import ch.brotzilla.util.MersenneTwister;

public class Test {

    private static class TestRng implements Rng {

        private final MersenneTwister rng;
        
        public TestRng(long seed) {
            rng = new MersenneTwister(seed);
        }
        
        public int nextInt() {
            return rng.nextInt();
        }

        public int nextInt(int n) {
            return rng.nextInt(n);
        }

        public int nextInt(int low, int high) {
            throw new UnsupportedOperationException();
        }

        public double nextDouble() {
            return rng.nextDouble();
        }
        
    }

    private static String format(int i) {
        if (i < 10) {
            return "00" + i;
        }
        if (i < 100) {
            return "0" + i;
        }
        return "" + i;
    }
    
    private static ActivationFunction getRandomActivationFunction(Rng rng) {
        switch (rng.nextInt(5)) {
        case 0:
            return new ExtendedPeriodicCosFunction();
        case 1: 
            return new ExtendedPeriodicTriangleFunction();
        case 2: 
            return new ExtendedDecliningCosFunction();
        case 3:
            return new ExtendedPeriodicTriangleFunction();
        case 4:
            return new ExtendedTanhFunction();
        default:
            throw new UnsupportedOperationException();
        }
    }
    
    public static void main(String[] args) throws IOException {
        
        final ImageType type = ImageType.ColorAlpha;
        final HistoryList historyList = new HistoryList();
        final TestRng rng = new TestRng(new Random().nextLong());
        
        for (int i = 0; i < 100; i++) {
            final Genome genome = Genomes.createMinimalGenome(false, 2, type.getPatternGeneratorOutputSize(), getRandomActivationFunction(rng), rng, historyList);
//          final Genome genome = Genomes.createDoublePerceptronGenome(false, 2, rng.nextInt(5) + 1, rng.nextInt(5) + 1, type.getPatternGeneratorOutputSize(), getRandomActivationFunction(rng), getRandomActivationFunction(rng), rng, historyList);
//            final Genome genome = Genomes.createPerceptronGenome(false, 2, rng.nextInt(5) + 1, type.getPatternGeneratorOutputSize(), getRandomActivationFunction(rng), getRandomActivationFunction(rng), rng, historyList);
            final NEATGenomeExpressor expressor = new NEATGenomeExpressor();
            final PatternGenerator pattern = new BasicPatternGenerator(expressor.express(genome));
            final ImageGenerator generator = new ImageGenerator(600, 600, type, pattern);
            generator.getSection().setBounds(-6, -6, 12, 12);

            generator.generate();

            ImageIO.write(generator.getImage(), "PNG", new File("./pics/test" + format(i) + ".png"));
        }
        
    }

}

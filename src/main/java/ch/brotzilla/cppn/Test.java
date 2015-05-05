package ch.brotzilla.cppn;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import ch.brotzilla.neat.evolution.Rng;
import ch.brotzilla.neat.expression.NEATGenomeExpressor;
import ch.brotzilla.neat.genome.Genome;
import ch.brotzilla.neat.genome.Genomes;
import ch.brotzilla.neat.history.HistoryList;
import ch.brotzilla.neat.math.ExtendedDecliningCosFunction;
import ch.brotzilla.neat.math.ExtendedPeriodicCosFunction;
import ch.brotzilla.neat.math.ExtendedPeriodicSawtoothFunction;
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

    public static void main(String[] args) throws IOException {
        
        final HistoryList historyList = new HistoryList();
        final Genome genome = Genomes.createDoublePerceptronGenome(false, 2, 5, 4, 1, new ExtendedPeriodicCosFunction(), new ExtendedTanhFunction(), new TestRng(new Random().nextLong()), historyList);
        final NEATGenomeExpressor expressor = new NEATGenomeExpressor();
        final PatternGenerator pattern = new SimplePatternGenerator(expressor.express(genome));
        final ImageGenerator generator = new ImageGenerator(1500, 1500, ImageType.Gray, pattern);
        generator.setSectorBounds(-1, -1, 2, 2);

        generator.generate();
        
        ImageIO.write(generator.getImage(), "PNG", new File("./test.png"));
        
    }

}

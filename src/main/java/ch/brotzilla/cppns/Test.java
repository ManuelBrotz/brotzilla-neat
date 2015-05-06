package ch.brotzilla.cppns;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import ch.brotzilla.cppns.genomes.ActivationFunctionProvider;
import ch.brotzilla.cppns.genomes.BasicGenomeGenerator;
import ch.brotzilla.cppns.genomes.GenomeGenerator;
import ch.brotzilla.cppns.genomes.StochasticActivationFunctionProvider;
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
import ch.brotzilla.neat.math.ExtendedDecliningSawtoothFunction;
import ch.brotzilla.neat.math.ExtendedDecliningSquareFunction;
import ch.brotzilla.neat.math.ExtendedDecliningTriangleFunction;
import ch.brotzilla.neat.math.ExtendedGaussianFunction;
import ch.brotzilla.neat.math.ExtendedLinearFunction;
import ch.brotzilla.neat.math.ExtendedLogFunction;
import ch.brotzilla.neat.math.ExtendedModifiedTanhFunction;
import ch.brotzilla.neat.math.ExtendedPeriodicCosFunction;
import ch.brotzilla.neat.math.ExtendedPeriodicSawtoothFunction;
import ch.brotzilla.neat.math.ExtendedPeriodicSquareFunction;
import ch.brotzilla.neat.math.ExtendedPeriodicTriangleFunction;
import ch.brotzilla.neat.math.ExtendedSignumFunction;
import ch.brotzilla.neat.math.ExtendedSqrtFunction;
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
    
    private static ActivationFunctionProvider getActivationFunctionProvider() {
        final StochasticActivationFunctionProvider result = new StochasticActivationFunctionProvider();
//        result.add(new ExtendedDecliningCosFunction(), 1);
//        result.add(new ExtendedDecliningSawtoothFunction(), 1);
//        result.add(new ExtendedDecliningSquareFunction(), 1);
//        result.add(new ExtendedDecliningTriangleFunction(), 1);
//        result.add(new ExtendedPeriodicCosFunction(), 1);
        result.add(new ExtendedPeriodicSawtoothFunction(), 1);
        result.add(new ExtendedPeriodicSquareFunction(), 1);
        result.add(new ExtendedPeriodicTriangleFunction(), 1);
//        result.add(new ExtendedTanhFunction(), 1);
//        result.add(new ExtendedModifiedTanhFunction(), 1);
//        result.add(new ExtendedGaussianFunction(), 1);
//        result.add(new ExtendedLinearFunction(), 1);
//        result.add(new ExtendedLogFunction(), 1);
//        result.add(new ExtendedSignumFunction(), 1);
//        result.add(new ExtendedSqrtFunction(), 1);
        return result;
    }
    
    public static void main(String[] args) throws IOException {
        
        final ImageType type = ImageType.Gray;
        final HistoryList historyList = new HistoryList();
        final TestRng rng = new TestRng(new Random().nextLong());
        final ActivationFunctionProvider activationFunctionProvider = getActivationFunctionProvider();
        final BasicGenomeGenerator genomeGenerator = new BasicGenomeGenerator(historyList, activationFunctionProvider);
        genomeGenerator.setRecurrentLinkProbability(0.01);
        genomeGenerator.setSynapseLinkProbability(0.1);
        final NEATGenomeExpressor expressor = new NEATGenomeExpressor();
        
        for (int i = 0; i < 100; i++) {
            System.out.println("Generating image " + (i + 1) + " of 100...");
            final PatternGenerator pattern = new BasicPatternGenerator(expressor.express(genomeGenerator.generate(type, rng)));
            final ImageGenerator generator = new ImageGenerator(200, 200, type, pattern);
            generator.getSection().setBounds(-1, -1, 2, 2);
            generator.generate();
            ImageIO.write(generator.getImage(), "PNG", new File("./pics/test" + format(i) + ".png"));
        }
        
    }

}

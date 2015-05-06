package ch.brotzilla.cppns.genomes;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import ch.brotzilla.neat.evolution.Rng;
import ch.brotzilla.neat.math.ActivationFunction;

public class StochasticActivationFunctionProvider implements ActivationFunctionProvider {

    private final List<Entry> functions;
    
    private double computeTotalProbability() {
        double result = 0;
        for (final Entry e : functions) {
            result += e.probability;
        }
        return result;
    }
    
    public double[] computeSlots(int count, double total) {
        final double[] slots = new double[functions.size()];
        int slot = 0;
        double sum = 0;
        for (final Entry e : functions) {
            sum += e.probability / total;
            slots[slot++] = sum;
        }
        return slots;
    }
    
    private static class Entry {
        
        private final ActivationFunction function;
        private final double probability;
        
        public Entry(ActivationFunction function, double probability) {
            Preconditions.checkNotNull(function, "The parameter 'function' must not be null");
            Preconditions.checkArgument(probability > 0, "The parameter 'probability' has to be greater than zero");
            this.function = function;
            this.probability = probability;
        }
        
    }
    
    public StochasticActivationFunctionProvider() {
        functions = Lists.newArrayList();
    }

    public int size() {
        return functions.size();
    }
    
    public void add(ActivationFunction function, double probability) {
        functions.add(new Entry(function, probability));
    }
    
    public ActivationFunction[] provide(int count, Rng rng) {
        Preconditions.checkArgument(count > 0, "The parameter 'count' has to be greater than zero");
        Preconditions.checkNotNull(rng, "The paramter 'rng' must not be null");
        final ActivationFunction[] result = new ActivationFunction[count];
        final double pointerSize = rng.nextDouble() / count;
        final double total = computeTotalProbability();
        final double[] slots = computeSlots(count, total);
        int slot = 0;
        for (int i = 0; i < count; i++) {
            final double pointer = pointerSize * (i + 1);
            while (slots[slot] <= pointer) {
                ++slot;
            }
            result[i] = functions.get(slot).function;
        }
        return result;
    }

    public ActivationFunction provide(Rng rng) {
        return provide(1, rng)[0];
    }

}

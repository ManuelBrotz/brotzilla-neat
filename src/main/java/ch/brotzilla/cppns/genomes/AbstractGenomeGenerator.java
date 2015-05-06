package ch.brotzilla.cppns.genomes;

import ch.brotzilla.neat.evolution.Rng;
import ch.brotzilla.neat.history.HistoryList;
import ch.brotzilla.neat.math.ActivationFunction;

import com.google.common.base.Preconditions;

public abstract class AbstractGenomeGenerator implements GenomeGenerator {

    private final HistoryList historyList;
    private final ActivationFunctionProvider activationFunctionProvider;
    
    protected ActivationFunction[] provideActivationFunction(int count, Rng rng) {
        return activationFunctionProvider.provide(count, rng);
    }
    
    protected ActivationFunction provideActivationFunction(Rng rng) {
        return activationFunctionProvider.provide(rng);
    }
    
    public AbstractGenomeGenerator(HistoryList historyList, ActivationFunctionProvider activationFunctionProvider) {
        Preconditions.checkNotNull(historyList, "The parameter 'historyList' must not be null");
        Preconditions.checkNotNull(activationFunctionProvider, "The parameter 'activationFunctionProvider' must not be null");
        this.historyList = historyList;
        this.activationFunctionProvider = activationFunctionProvider;
    }
    
    public HistoryList getHistoryList() {
        return historyList;
    }
    
    public ActivationFunctionProvider getActivationFunctionProvider() {
        return activationFunctionProvider;
    }

}

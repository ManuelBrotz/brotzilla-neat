package ch.brotzilla.neat.math;

import java.util.List;

import com.google.common.base.Preconditions;

public abstract class SimpleActivationFunction extends ActivationFunction {

    @Override
    protected final void initializeDefaultSynapses(List<ActivationFunctionSynapse> synapses) {}

    protected abstract double _compute(double[] synapses);

    protected SimpleActivationFunction(String id, String name, String description) {
        super(id, name, description);
    }

    @Override
    public final double compute(double[] synapses) {
        Preconditions.checkNotNull(synapses, "The parameter 'synapses' must not be null");
        Preconditions.checkArgument(synapses.length == 1, "The length of the parameter 'synapses' has to be equal to 1");
        return _compute(synapses);
    }

}

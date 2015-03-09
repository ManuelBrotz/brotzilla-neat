package ch.brotzilla.neat.math;

import java.util.List;

import com.google.common.base.Preconditions;

public abstract class SimpleActivationFunction extends ActivationFunction {

    @Override
    protected final void initializeDefaultSynapses(List<ActivationFunctionSynapse> synapses) {}

    protected abstract double _compute(double activation, double[] synapses);

    protected SimpleActivationFunction(String id, String name, String description) {
        super(id, name, description, (ActivationFunctionSynapse[]) null);
    }

    @Override
    public final double compute(double activation, double[] synapses) {
        Preconditions.checkArgument(synapses == null, "The parameter 'synapses' has to be null");
        return _compute(activation, synapses);
    }

}

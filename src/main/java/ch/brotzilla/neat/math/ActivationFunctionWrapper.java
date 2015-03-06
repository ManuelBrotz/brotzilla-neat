package ch.brotzilla.neat.math;

import java.util.Arrays;

import com.google.common.base.Preconditions;

public class ActivationFunctionWrapper {

    private final ActivationFunction activationFunction;
    private final double[] synapseDefaults;
    
    public ActivationFunctionWrapper(ActivationFunction activationFunction, double[] synapseDefaults) {
        Preconditions.checkNotNull(activationFunction, "The parameter 'activationFunction' must not be null");
        this.activationFunction = activationFunction;
        if (activationFunction.getNumberOfSynapses() > 0) {
            Preconditions.checkNotNull(synapseDefaults, "The parameter 'synapseDefaults' must not be null");
            Preconditions.checkArgument(synapseDefaults.length == activationFunction.getNumberOfSynapses(), "The length of the parameter 'synapseDefaults' has to be equal to " + activationFunction.getNumberOfSynapses());
            this.synapseDefaults = Arrays.copyOf(synapseDefaults, synapseDefaults.length);
        } else {
            Preconditions.checkArgument(synapseDefaults == null, "The parameter 'synapseDefaults' has to be null");
            this.synapseDefaults = null;
        }
    }
    
    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }
    
    public double[] getSynapseDefaults() {
        return synapseDefaults;
    }
    
    public double compute(double activation) {
        return activationFunction.compute(activation, synapseDefaults);
    }

}

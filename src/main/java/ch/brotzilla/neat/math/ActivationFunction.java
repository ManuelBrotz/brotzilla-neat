package ch.brotzilla.neat.math;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public abstract class ActivationFunction {

    public static final double Pi = Math.PI;
    public static final double TwoPi = 2 * Math.PI;

    private final String id, name, description;
    private final List<ActivationFunctionSynapse> synapses;
    private final int numberOfSynapses;
    
    private List<ActivationFunctionSynapse> initializeSynapses(ActivationFunctionSynapse[] synapses) {
        final List<ActivationFunctionSynapse> result = Lists.newArrayList();
        initializeDefaultSynapses(result);
        if (synapses != null) {
            for (final ActivationFunctionSynapse synapse : synapses) {
                Preconditions.checkNotNull(synapse, "Activation function synapses must not be null");
                result.add(synapse);
            }
        }
        return Collections.unmodifiableList(result);
    }
    
    protected abstract void initializeDefaultSynapses(List<ActivationFunctionSynapse> synapses);
    
    protected abstract double _compute(double activation, double[] synapses);

    protected ActivationFunction(String id, String name, String description, ActivationFunctionSynapse... synapses) {
        Preconditions.checkNotNull(id, "The parameter 'id' must not be null");
        Preconditions.checkArgument(!id.trim().isEmpty(), "The parameter 'id' must not be empty");
        Preconditions.checkNotNull(name, "The parameter 'name' must not be null");
        Preconditions.checkArgument(!name.trim().isEmpty(), "The parameter 'name' must not be empty");
        Preconditions.checkNotNull(description, "The parameter 'description' must not be null");
        Preconditions.checkArgument(!description.trim().isEmpty(), "The parameter 'description' must not be empty");
        this.id = id;
        this.name = name;
        this.description = description;
        this.synapses = initializeSynapses(synapses);
        this.numberOfSynapses = this.synapses.size();
    }
    
    public String getID() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }

    public List<ActivationFunctionSynapse> getSynapses() {
        return synapses;
    }
    
    public int getNumberOfSynapses() {
        return numberOfSynapses;
    }
    
    public double[] copySynapseDefaults() {
        if (numberOfSynapses > 0) { 
            final double[] result = new double[numberOfSynapses];
            int i = 0;
            for (final ActivationFunctionSynapse synapse : synapses) {
                result[i++] = synapse.getDefaultValue();
            }
            return result;
        }
        return null;
    }

    public double getSynapseDefault(int index) {
        return synapses.get(index).getDefaultValue();
    }
    
    public void setSynapseDefault(int synapse, double value) {
        synapses.get(synapse).setDefaultValue(value);
    }
    
    public abstract double compute(double activation, double[] synapses);

}

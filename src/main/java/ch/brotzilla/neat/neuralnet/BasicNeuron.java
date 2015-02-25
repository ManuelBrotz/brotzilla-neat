package ch.brotzilla.neat.neuralnet;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import ch.brotzilla.neat.math.ActivationFunction;

public class BasicNeuron extends Neuron {

    private final List<Connection> incomingConnections;
    private ActivationFunction activationFunction;
    private double[] inputSynapses;
    private double activation;

    public BasicNeuron(NeuralNet owner, ActivationFunction activationFunction) {
        super(owner);
        Preconditions.checkNotNull(activationFunction, "The parameter 'activationFunction' must not be null");
        incomingConnections = Lists.newArrayList();
        setActivationFunction(activationFunction);
    }
    
    public List<Connection> getIncomingConnections() {
        return incomingConnections;
    }

    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }
    
    public void setActivationFunction(ActivationFunction value) {
        Preconditions.checkNotNull(value, "The parameter 'value' must not be null");
        activationFunction = value;
        inputSynapses = new double[value.getNumberOfParameters()];
    }
    
    @Override
    public double getActivation() {
        return activation;
    }
    
    public void compute() {
        for (int i = 0; i < inputSynapses.length; i++) {
            inputSynapses[i] = 0d;
        }
        for (final Connection connection : incomingConnections) {
            final int synapse = connection.getSynapse();
            if (synapse < inputSynapses.length) {
                inputSynapses[synapse] += connection.getInputNeuron().getActivation() * connection.getWeight();
            }
        }
        activation = activationFunction.compute(inputSynapses);
    }

}

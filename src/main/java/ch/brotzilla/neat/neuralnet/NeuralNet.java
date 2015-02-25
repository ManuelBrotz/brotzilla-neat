package ch.brotzilla.neat.neuralnet;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public class NeuralNet {

    private final InputNeuron biasNeuron;
    private final List<InputNeuron> inputNeurons;
    private final List<BasicNeuron> hiddenNeurons;
    private final List<BasicNeuron> outputNeurons;
    private final List<Connection> connections;
    
    public NeuralNet() {
        this.biasNeuron = new InputNeuron(this, 1.0d);
        this.inputNeurons = Lists.newArrayList();
        this.hiddenNeurons = Lists.newArrayList();
        this.outputNeurons = Lists.newArrayList();
        this.connections = Lists.newArrayList();
    }
    
    public InputNeuron getBiasNeuron() {
        return biasNeuron;
    }
    
    public List<InputNeuron> getInputNeurons() {
        return inputNeurons;
    }
    
    public List<BasicNeuron> getHiddenNeurons() {
        return hiddenNeurons;
    }
    
    public List<BasicNeuron> getOuBasicNeurons() {
        return outputNeurons;
    }
    
    public List<Connection> getConnections() {
        return connections;
    }

    public int getTotalNumberOfNeurons() {
        return 1 + inputNeurons.size() + hiddenNeurons.size() + outputNeurons.size();
    }
    
    public Neuron getNeuron(int index) {
        final int inputSize = inputNeurons.size(), hiddenSize = hiddenNeurons.size();
        Preconditions.checkElementIndex(index, 1 + inputSize + hiddenSize + outputNeurons.size());
        if (index == 0) {
            return biasNeuron;
        }
        index -= 1;
        if (index < inputSize) {
            return inputNeurons.get(index);
        }
        index -= inputSize;
        if (index < hiddenSize) {
            return hiddenNeurons.get(index);
        }
        index -= hiddenSize;
        return outputNeurons.get(index);
    }
    
    public void updateIncomingConnections() {
        for (final BasicNeuron neuron : hiddenNeurons) {
            neuron.getIncomingConnections().clear();
        }
        for (final BasicNeuron neuron : outputNeurons) {
            neuron.getIncomingConnections().clear();
        }
        for (final Connection connection : connections) {
            if (connection.getSynapse() < connection.getOutputNeuron().getActivationFunction().getNumberOfParameters()) {
                connection.getOutputNeuron().getIncomingConnections().add(connection);
            }
        }
    }
    
    public void compute(double[] input) {
        Preconditions.checkNotNull(input, "The parameter 'input' must not be null");
        Preconditions.checkArgument(input.length == inputNeurons.size(), "The length of the parameter 'input' (" + input.length + ") has to be equal to the number of input neurons (" + inputNeurons.size() + ")");
        int index = 0;
        for (final InputNeuron n : inputNeurons) {
            n.setActivation(input[index++]);
        }
        for (final BasicNeuron n : hiddenNeurons) {
            n.compute();
        }
        for (final BasicNeuron n : outputNeurons) {
            n.compute();
        }
    }
    
    public double[] compute(double[] input, double[] output) {
        Preconditions.checkNotNull(input, "The parameter 'input' must not be null");
        Preconditions.checkArgument(input.length == inputNeurons.size(), "The length of the parameter 'input' (" + input.length + ") has to be equal to the number of input neurons (" + inputNeurons.size() + ")");
        if (output == null || output.length < outputNeurons.size()) {
            output = new double[outputNeurons.size()];
        }
        int index = 0;
        for (final InputNeuron n : inputNeurons) {
            n.setActivation(input[index++]);
        }
        for (final BasicNeuron n : hiddenNeurons) {
            n.compute();
        }
        index = 0;
        for (final BasicNeuron n : outputNeurons) {
            n.compute();
            output[index++] = n.getActivation();
        }
        return output;
    }
}

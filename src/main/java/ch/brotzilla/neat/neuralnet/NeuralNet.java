package ch.brotzilla.neat.neuralnet;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Preconditions;

// TODO Add NeuronType to class Neuron to make it possible to distinguish hidden/output neurons. For preconditions.
public class NeuralNet {

    private final int numberOfInputNeurons, numberOfHiddenNeurons, numberOfOutputNeurons;
    private final List<Neuron> hiddenNeurons, outputNeurons;
    private double[] inputBuffer, hiddenBuffer, outputBuffer;
    
    double getInputNeuronActivation(int neuronIndex) {
        return inputBuffer[neuronIndex];
    }

    double getHiddenNeuronActivation(int neuronIndex) {
        return hiddenBuffer[neuronIndex];
    }

    void setHiddenNeuronActivation(int neuronIndex, double activation) {
        hiddenBuffer[neuronIndex] = activation;
    }

    double getOutputNeuronActivation(int neuronIndex) {
        return outputBuffer[neuronIndex];
    }

    void setOutputNeuronActivation(int neuronIndex, double activation) {
        outputBuffer[neuronIndex] = activation;
    }

    public NeuralNet(int numberOfInputNeurons, int numberOfHiddenNeurons, int numberOfOutputNeurons, Neuron[] hiddenNeurons, Neuron[] outputNeurons) {
        Preconditions.checkArgument(numberOfInputNeurons > 0, "The parameter 'numberOfInputNeurons' has to be greater than zero");
        Preconditions.checkArgument(numberOfHiddenNeurons >= 0, "The parameter 'numberOfHiddenNeurons' has to be greater than or equal to zero");
        Preconditions.checkArgument(numberOfOutputNeurons > 0, "The parameter 'numberOfOutputNeurons' has to be greater than zero");
        if (numberOfHiddenNeurons > 0) {
            Preconditions.checkNotNull(hiddenNeurons, "The parameter 'hiddenNeurons' must not be null");
            Preconditions.checkArgument(hiddenNeurons.length == numberOfHiddenNeurons, "The length of the parameter 'hiddenNeurons' has to be equal to " + numberOfHiddenNeurons);
            for (int i = 0; i < hiddenNeurons.length; i++) {
                final Neuron n = hiddenNeurons[i];
                Preconditions.checkNotNull(n, "The parameter 'hiddenNeurons[" + i + "]' must not be null");
                Preconditions.checkElementIndex(n.getNeuronIndex(), numberOfHiddenNeurons, "The neuron index of the parameter 'hiddenNeurons[" + i + "]'");
            }
        } else {
            Preconditions.checkArgument(hiddenNeurons == null, "The parameter 'hiddenNeurons' has to be null");
        }
        Preconditions.checkNotNull(outputNeurons, "The parameter 'outputNeurons' must not be null");
        Preconditions.checkArgument(outputNeurons.length == numberOfOutputNeurons, "The length of the parameter 'outputNeurons' has to be equal to " + numberOfOutputNeurons);
        for (int i = 0; i < outputNeurons.length; i++) {
            final Neuron n = outputNeurons[i];
            Preconditions.checkNotNull(n, "The parameter 'outputNeurons[" + i + "]' must not be null");
            Preconditions.checkElementIndex(n.getNeuronIndex(), numberOfOutputNeurons, "The neuron index of the parameter 'outputNeurons[" + i + "]'");
        }
        this.numberOfInputNeurons = numberOfInputNeurons;
        this.numberOfHiddenNeurons = numberOfHiddenNeurons;
        this.numberOfOutputNeurons = numberOfOutputNeurons;
        this.hiddenNeurons = Collections.unmodifiableList(Arrays.asList(hiddenNeurons));
        this.outputNeurons = Collections.unmodifiableList(Arrays.asList(outputNeurons));
    }

    public int getNumberOfInputNeurons() {
        return numberOfInputNeurons;
    }

    public int getNumberOfHiddenNeurons() {
        return numberOfHiddenNeurons;
    }

    public int getNumberOfOutputNeurons() {
        return numberOfOutputNeurons;
    }

    public List<Neuron> getHiddenNeurons() {
        return hiddenNeurons;
    }
    
    public List<Neuron> getOutputNeurons() {
        return outputNeurons;
    }
    
    public void compute(double[] inputBuffer, double[] hiddenBuffer, double[] outputBuffer) {
        Preconditions.checkNotNull(inputBuffer, "The parameter 'inputBuffer' must not be null");
        Preconditions.checkArgument(inputBuffer.length == numberOfInputNeurons, "The length of the parameter 'inputBuffer' has to be equal to " + numberOfInputNeurons);
        Preconditions.checkNotNull(hiddenBuffer, "The parameter 'hiddenBuffer' must not be null");
        Preconditions.checkArgument(hiddenBuffer.length == numberOfHiddenNeurons, "The length of the parameter 'hiddenBuffer' has to be equal to " + numberOfHiddenNeurons);
        Preconditions.checkNotNull(outputBuffer, "The parameter 'outputBuffer' must not be null");
        Preconditions.checkArgument(outputBuffer.length == numberOfOutputNeurons, "The length of the parameter 'outputBuffer' has to be equal to " + numberOfOutputNeurons);
        this.inputBuffer = inputBuffer;
        this.hiddenBuffer = hiddenBuffer;
        this.outputBuffer = outputBuffer;
        try {
            for (final Neuron n : hiddenNeurons) {
                n.compute(this);
            }
            for (final Neuron n : outputNeurons) {
                n.compute(this);
            }
        } finally {
            this.inputBuffer = null;
            this.hiddenBuffer = null;
            this.outputBuffer = null;
        }
    }

}

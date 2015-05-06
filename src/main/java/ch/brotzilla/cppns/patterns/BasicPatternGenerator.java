package ch.brotzilla.cppns.patterns;

import ch.brotzilla.neat.neuralnet.NeuralNet;

import com.google.common.base.Preconditions;

public class BasicPatternGenerator implements PatternGenerator {

    private final NeuralNet nn;
    private final double[] input, hidden;
    private final int outputSize;
    
    public BasicPatternGenerator(NeuralNet nn) {
        Preconditions.checkNotNull(nn, "The parameter 'nn' must not be null");
        Preconditions.checkArgument(nn.getNumberOfInputNeurons() == 2, "The number of input neurons has to be equal to 2");
        Preconditions.checkArgument(nn.getNumberOfOutputNeurons() >= 1 && nn.getNumberOfOutputNeurons() <= 4, "The number of output neurons has to be in the range from 1 to 4");
        this.nn = nn;
        input = new double[2];
        hidden = new double[nn.getNumberOfHiddenNeurons()];
        outputSize = nn.getNumberOfOutputNeurons();
    }
    
    public NeuralNet getNeuralNet() {
        return nn;
    }
    
    public int getOutputSize() {
        return outputSize;
    }
    
    public void generate(double x, double y, double[] output) {
        Preconditions.checkNotNull(output, "The parameter 'output' must not be null");
        Preconditions.checkArgument(output.length == outputSize, "The length of the parameter 'output' has to be equal to " + outputSize);
        input[0] = x;
        input[1] = y;
        nn.compute(input, hidden, output);
    }

}

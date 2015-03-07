package ch.brotzilla.neat.artspace.generator;

import com.google.common.base.Preconditions;

public abstract class InputLayer {

    protected final int numberOfInputNeurons, numberOfParameters;

    public abstract double[] _compute(double x, double y, double[] parameters, double[] output);

    protected InputLayer(int numberOfInputNeurons, int numberOfParameters) {
        Preconditions.checkArgument(numberOfInputNeurons > 0, "The parameter 'numberOfInputNeurons' has to be greater than zero");
        Preconditions.checkArgument(numberOfParameters >= 0, "The parameter 'numberOfParameters' has to be greater than or equal to zero");
        this.numberOfInputNeurons = numberOfInputNeurons;
        this.numberOfParameters = numberOfParameters;
    }
    
    public int getNumberOfInputNeurons() {
        return numberOfInputNeurons;
    }
    
    public int getNumberOfParameters() {
        return numberOfParameters;
    }
    
    public final double[] compute(double x, double y, double[] parameters, double[] output) {
        if (numberOfParameters == 0) {
            Preconditions.checkArgument(parameters == null, "The parameter 'parameters' has to be null");
        } else {
            Preconditions.checkNotNull(parameters, "The parameter 'parameters' must not be null");
            Preconditions.checkArgument(parameters.length == numberOfParameters, "The length of the parameter 'parameters' has to be equal to " + numberOfParameters);
        }
        if (output == null) {
            output = new double[numberOfInputNeurons];
        } else {
            Preconditions.checkArgument(output.length == numberOfInputNeurons, "The length of the parameter 'output' has to be equal to " + numberOfInputNeurons);
        }
        final double[] result = _compute(x, y, parameters, output);
        Preconditions.checkState(result == output, "The internal method '_compute()' has to return its parameter 'output'");
        return result;
    }

}

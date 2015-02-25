package ch.brotzilla.neat.math;

import com.google.common.base.Preconditions;

public abstract class ActivationFunction {

    protected abstract int _getNumberOfParameters();
    
    protected abstract double _compute(double[] parameters);
    
    public ActivationFunction() {}
    
    public final int getNumberOfParameters() {
        final int result = _getNumberOfParameters();
        Preconditions.checkState(result > 0, "Internal Error! The result of the function '_getNumberOfParameters()' has to be greater than zero");
        return result;
    }
    
    public final double compute(double[] parameters) {
        final int expectedLength = getNumberOfParameters();
        Preconditions.checkNotNull(parameters, "The parameter 'parameters' must not be null");
        Preconditions.checkArgument(parameters.length == expectedLength, "The length of the parameter 'parameters' has to be equal to " + expectedLength);
        return _compute(parameters);
    }
    
    public abstract double getDefault(int parameter);
    
    public abstract double getLowerBound(int parameter);
    
    public abstract double getUpperBound(int parameter);

}

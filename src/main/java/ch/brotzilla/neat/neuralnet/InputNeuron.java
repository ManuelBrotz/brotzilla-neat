package ch.brotzilla.neat.neuralnet;

public class InputNeuron extends Neuron {

    private double activation;
    
    public InputNeuron(NeuralNet owner) {
        super(owner);
    }
    
    public InputNeuron(NeuralNet owner, double activation) {
        super(owner);
        this.activation = activation;
    }
    
    public void setActivation(double activation) {
        this.activation = activation;
    }
    
    @Override
    public double getActivation() {
        return activation;
    }

}

package ch.brotzilla.neat.neuralnet;

public enum NodeType {
    
    Bias, Input, Output, Hidden;
    
    public boolean isSourceNode() {
        return this == Bias || this == Input;
    }
    
    public boolean isTargetNode() {
        return this == Output || this == Hidden;
    }
    
}

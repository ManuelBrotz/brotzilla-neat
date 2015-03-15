package ch.brotzilla.neat.genome;

import java.util.List;

public enum NodeType {
    
    Bias {

        @Override
        List<Node> selectNodesList(List<Node> inputNodes, List<Node> hiddenNodes, List<Node> outputNodes) {
            throw new UnsupportedOperationException();
        }
        
    }, 
    
    Input {

        @Override
        List<Node> selectNodesList(List<Node> inputNodes, List<Node> hiddenNodes, List<Node> outputNodes) {
            return inputNodes;
        }
        
    }, 
    
    Hidden {

        @Override
        List<Node> selectNodesList(List<Node> inputNodes, List<Node> hiddenNodes, List<Node> outputNodes) {
            return hiddenNodes;
        }
        
    },
    
    Output {

        @Override
        List<Node> selectNodesList(List<Node> inputNodes, List<Node> hiddenNodes, List<Node> outputNodes) {
            return outputNodes;
        }
        
    }; 
    
    public boolean isInputNode() {
        return this == Bias || this == Input;
    }
    
    public boolean isTargetNode() {
        return this == Output || this == Hidden;
    }
    
    abstract List<Node> selectNodesList(List<Node> inputNodes, List<Node> hiddenNodes, List<Node> outputNodes);
    
}

package ch.brotzilla.neat.genome;

import java.util.List;

public enum NodeType {
    
    Bias {

        @Override
        public List<Node> selectNodesList(List<Node> inputNodes, List<Node> hiddenNodes, List<Node> outputNodes) {
            throw new UnsupportedOperationException();
        }
        
    }, 
    
    Input {

        @Override
        public List<Node> selectNodesList(List<Node> inputNodes, List<Node> hiddenNodes, List<Node> outputNodes) {
            return inputNodes;
        }
        
    }, 
    
    Hidden {

        @Override
        public List<Node> selectNodesList(List<Node> inputNodes, List<Node> hiddenNodes, List<Node> outputNodes) {
            return hiddenNodes;
        }
        
    },
    
    Output {

        @Override
        public List<Node> selectNodesList(List<Node> inputNodes, List<Node> hiddenNodes, List<Node> outputNodes) {
            return outputNodes;
        }
        
    }; 
    
    public boolean isInputNode() {
        return this == Bias || this == Input;
    }
    
    public boolean isTargetNode() {
        return this == Output || this == Hidden;
    }
    
    public abstract List<Node> selectNodesList(List<Node> inputNodes, List<Node> hiddenNodes, List<Node> outputNodes);
    
}

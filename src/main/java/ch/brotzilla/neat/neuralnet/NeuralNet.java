package ch.brotzilla.neat.neuralnet;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

public class NeuralNet {

    private final List<Neuron> nodes, nodesWrapper;
    private final List<Connection> links, linksWrapper;
    
    public NeuralNet() {
        nodes = Lists.newArrayList();
        nodesWrapper = Collections.unmodifiableList(nodes);
        links = Lists.newArrayList();
        linksWrapper = Collections.unmodifiableList(links);
    }

    public List<Neuron> getNodes() {
        return nodesWrapper;
    }
    
    public List<Connection> getLinks() {
        return linksWrapper;
    }
    
}

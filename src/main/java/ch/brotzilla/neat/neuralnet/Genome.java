package ch.brotzilla.neat.neuralnet;

import java.util.Collections;
import java.util.List;

import com.google.common.collect.Lists;

public class Genome {

    private final List<Node> nodes, nodesWrapper;
    private final List<Link> links, linksWrapper;
    
    public Genome() {
        nodes = Lists.newArrayList();
        nodesWrapper = Collections.unmodifiableList(nodes);
        links = Lists.newArrayList();
        linksWrapper = Collections.unmodifiableList(links);
    }

    public List<Node> getNodes() {
        return nodesWrapper;
    }
    
    public List<Link> getLinks() {
        return linksWrapper;
    }
    
}

package ch.brotzilla.neat.evolution.speciation;

import gnu.trove.map.TIntDoubleMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntDoubleHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import ch.brotzilla.neat.genome.Genome;
import ch.brotzilla.neat.genome.Link;
import ch.brotzilla.neat.genome.Node;

import com.google.common.base.Preconditions;

public class Centroid {
    
    private final TIntObjectMap<double[]> nodes;
    private final TIntDoubleMap links;
    
    public Centroid(Genome genome) {
        Preconditions.checkNotNull(genome, "The parameter 'genome' must not be null");
        nodes = new TIntObjectHashMap<double[]>();
        links = new TIntDoubleHashMap();
        for (final Node node : genome) {
            nodes.put(node.getInnovationNumber(), node.copySynapseDefaults());
        }
        for (final Link link : genome.getLinks()) {
            links.put(link.getInnovationNumber(), link.getWeight());
        }
    }
    
    public Centroid(TIntObjectMap<double[]> nodes, TIntDoubleMap links) {
        Preconditions.checkNotNull(nodes, "The parameter 'nodes' must not be null");
        Preconditions.checkNotNull(links, "The parameter 'links' must not be null");
        this.nodes = nodes;
        this.links = links;
    }
    
    public TIntObjectMap<double[]> getNodes() {
        return nodes;
    }
    
    public TIntDoubleMap getLinks() {
        return links;
    }
    
}
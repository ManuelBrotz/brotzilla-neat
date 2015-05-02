package ch.brotzilla.neat.evolution.speciation;

import gnu.trove.map.TIntDoubleMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntDoubleHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import ch.brotzilla.neat.Debug;
import ch.brotzilla.neat.genome.Genome;
import ch.brotzilla.neat.genome.Link;
import ch.brotzilla.neat.genome.Node;

import com.google.common.base.Preconditions;

public class CentroidCalculator {
    
    private final TIntObjectMap<NodeEntry> nodes;
    private final TIntObjectMap<LinkEntry> links;
    
    public CentroidCalculator() {
        nodes = new TIntObjectHashMap<NodeEntry>();
        links = new TIntObjectHashMap<LinkEntry>();
    }
    
    public void add(Genome genome) {
        Preconditions.checkNotNull(genome, "The parameter 'genome' must not be null");
        for (final Node node : genome) {
            NodeEntry entry = nodes.get(node.getInnovationNumber());
            if (entry == null) {
                entry = new NodeEntry(node);
                nodes.put(node.getInnovationNumber(), entry);
            } else {
                entry.add(node);
            }
        }
        for (final Link link : genome.getLinks()) {
            LinkEntry entry = links.get(link.getInnovationNumber());
            if (entry == null) {
                entry = new LinkEntry(link);
                links.put(link.getInnovationNumber(), entry);
            } else {
                entry.add(link);
            }
        }
    }
    
    public Centroid compute() {
        final TIntObjectMap<double[]> nc = new TIntObjectHashMap<double[]>();
        final TIntDoubleMap lc = new TIntDoubleHashMap();
        for (final NodeEntry entry : nodes.valueCollection()) {
            if (Debug.EnableIntegrityChecks) {
                Preconditions.checkState(!nc.containsKey(entry.id), "Node entries have to be unique");
            }
            nc.put(entry.id, entry.compute());
        }
        for (final LinkEntry entry : links.valueCollection()) {
            if (Debug.EnableIntegrityChecks) {
                Preconditions.checkState(!lc.containsKey(entry.id), "Link entries have to be unique");
            }
            lc.put(entry.id, entry.compute());
        }
        return new Centroid(nc, lc);
    }
    
    public void clear() {
        nodes.clear();
        links.clear();
    }
    
    private static class NodeEntry {
        
        private final int id;
        private int count;
        private final double[] totals;
        
        public NodeEntry(Node node) {
            Preconditions.checkNotNull(node, "The parameter 'node' must not be null");
            id = node.getInnovationNumber();
            count = 1;
            totals = node.copySynapseDefaults();
        }
        
        public void add(Node node) {
            Preconditions.checkNotNull(node, "The parameter 'node' must not be null");
            Preconditions.checkArgument(node.getInnovationNumber() == id, "The innovation number of the node has to be " + id);
            final double[] synapses = node.getSynapseDefaults();
            Preconditions.checkState(totals.length == synapses.length, "The node with the id " + node.getInnovationNumber() + " is expected to have exactly " + totals.length + " synapses");
            count++;
            for (int i = 0; i < synapses.length; i++) {
                totals[i] += synapses[i];
            }
        }
        
        public double[] compute() {
            final double[] result = new double[totals.length];
            for (int i = 0; i < result.length; i++) {
                result[i] = totals[i] / count;
            }
            return result;
        }
        
    }

    private static class LinkEntry {
        
        private final int id;
        private int count;
        private double total;
        
        public LinkEntry(Link link) {
            Preconditions.checkNotNull(link, "The parameter 'link' must not be null");
            id = link.getInnovationNumber();
            count = 1;
            total = link.getWeight();
        }
        
        public void add(Link link) {
            Preconditions.checkNotNull(link, "The parameter 'link' must not be null");
            Preconditions.checkArgument(link.getInnovationNumber() == id, "The innovation number of the link has to be " + id);
            count++;
            total += link.getWeight();
        }
        
        public double compute() {
            return total / count;
        }
        
    }

}
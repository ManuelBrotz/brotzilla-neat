package ch.brotzilla.neat.evolution.speciation;

import java.util.Arrays;

import gnu.trove.impl.Constants;
import gnu.trove.map.TIntDoubleMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntDoubleHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.procedure.TIntDoubleProcedure;
import gnu.trove.procedure.TIntObjectProcedure;
import ch.brotzilla.neat.genome.Genome;
import ch.brotzilla.neat.genome.Link;
import ch.brotzilla.neat.genome.Node;

import com.google.common.base.Preconditions;

public class Centroid {
    
    private final TIntObjectMap<double[]> nodes;
    private final TIntDoubleMap links;
    
    Centroid(TIntObjectMap<double[]> nodes, TIntDoubleMap links) {
        Preconditions.checkNotNull(nodes, "The parameter 'nodes' must not be null");
        Preconditions.checkNotNull(links, "The parameter 'links' must not be null");
        this.nodes = nodes;
        this.links = links;
    }

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
    
    public Centroid(Centroid source) {
        Preconditions.checkNotNull(source, "The parameter 'source' must not be null");
        final TIntObjectMap<double[]> n = new TIntObjectHashMap<double[]>(Constants.DEFAULT_CAPACITY, Constants.DEFAULT_LOAD_FACTOR, 0);
        final TIntDoubleMap l = new TIntDoubleHashMap(Constants.DEFAULT_CAPACITY, Constants.DEFAULT_LOAD_FACTOR, 0, Double.NaN);
        source.nodes.forEachEntry(new TIntObjectProcedure<double[]>() {
            public boolean execute(int key, double[] value) {
                n.put(key, Arrays.copyOf(value, value.length));
                return true;
            }
        });
        source.links.forEachEntry(new TIntDoubleProcedure() {
            public boolean execute(int key, double value) {
                l.put(key, value);
                return true;
            }
        });
        this.nodes = n;
        this.links = l;
    }
    
    public double distance(Genome genome) {
        Preconditions.checkNotNull(genome, "The parameter 'genome' must not be null");
        double result = 0;
        for (final Node node : genome) {
            if (node.getType().isInputNode()) continue;
            final double[] a = node.getSynapseDefaults(), b = nodes.get(node.getInnovationNumber());
            if (b == null) {
                for (int i = 0; i < a.length; i++) {
                    result += a[i] * a[i];
                }
            } else {
                Preconditions.checkState(a.length == b.length, "Internal Error: Unable to compute distance");
                for (int i = 0; i < a.length; i++) {
                    final double d = a[i] - b[i];
                    result += d * d;
                }
            }
        }
        for (final Link link : genome.getLinks()) {
            final double a = link.getWeight();
            if (links.containsKey(link.getInnovationNumber())) {
                final double b = links.get(link.getInnovationNumber());
                final double d = a - b;
                result += d * d;
            } else {
                result += a * a;
            }
        }
        return result;
    }
    
    @Override
    public Centroid clone() {
        return new Centroid(this);
    }
    
}
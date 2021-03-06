package ch.brotzilla.neat.history;

public class LinkHistoryKey extends HistoryKey {
    
    @Override
    protected int computeHash() {
        return HistoryList.hashFunction.newHasher().putInt(sourceNode).putInt(targetNode).putInt(targetSynapse).hash().asInt();
    }
    
    public LinkHistoryKey(int sourceNode, int targetNode, int targetSynapse) {
        super(sourceNode, targetNode, targetSynapse);
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof LinkHistoryKey) {
            final LinkHistoryKey otherKey = (LinkHistoryKey) other;
            return sourceNode == otherKey.sourceNode 
                    && targetNode == otherKey.targetNode
                    && targetSynapse == otherKey.targetSynapse;
        }
        return false;
    }
    
}

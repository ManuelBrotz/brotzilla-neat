package ch.brotzilla.neat.evolution;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class HistoryList {
    
    public static final HashFunction hashFunction = Hashing.goodFastHash(32);
    
    public HistoryList() {
        
    }

}

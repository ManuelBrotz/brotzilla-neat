package ch.brotzilla.neat.evolution;

public interface Rng {
    
    String getName();

    int nextInt();
    
    int nextInt(int n);
    
    int nextInt(int low, int high);
    
    double nextDouble();
    
}

package ch.brotzilla.neat.evolution;

public interface RngProvider {
	
	long getGlobalSeed();
	
	Rng getGlobalRng();
	
	Rng getNamedRng(String name);
	
}

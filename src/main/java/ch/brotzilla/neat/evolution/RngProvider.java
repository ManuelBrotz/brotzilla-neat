package ch.brotzilla.neat.evolution;

import java.util.Random;

public interface RngProvider {
	
	long getGlobalSeed();
	
	Random getGlobalRng();
	
	Random getNamedRng(String name);
	
}

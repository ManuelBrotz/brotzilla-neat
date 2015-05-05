package ch.brotzilla.cppn;

public interface PatternGenerator {

    int getOutputSize();
    
    void generate(double x, double y, double[] output);
    
}

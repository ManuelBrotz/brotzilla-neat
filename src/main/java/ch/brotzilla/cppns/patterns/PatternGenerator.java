package ch.brotzilla.cppns.patterns;

public interface PatternGenerator {

    int getOutputSize();
    
    void generate(double x, double y, double[] output);
    
}

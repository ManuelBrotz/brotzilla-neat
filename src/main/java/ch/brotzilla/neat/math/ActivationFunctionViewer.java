package ch.brotzilla.neat.math;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

import com.google.common.base.Preconditions;

@SuppressWarnings("serial")
public class ActivationFunctionViewer extends JFrame {

    private final ActivationFunctionDisplay display;
    
    private ActivationFunction createFunction() {
        return new SawtoothWaveFunction();
    }
    
    private ActivationFunction setParams(ActivationFunction f, double[] p) {
        Preconditions.checkNotNull(f, "The parameter 'f' must not be null");
        Preconditions.checkArgument(p.length == f.getNumberOfParameters(), "The length of the parameter 'f' has to be equal to " + f.getNumberOfParameters());
        for (int i = 0; i < p.length; i++) {
            f.getParameterValues()[i] = p[i];
        }
        return f;
    }
    
    private void addFunctions() {
//        display.addFunction(setParams(createFunction(), new double[] {-1.0, 0.0, 1.0, 0.0}), Color.pink);
//        display.addFunction(setParams(createFunction(), new double[] {-1.0, 0.0, 0.5, 0.5}), Color.cyan);
//        display.addFunction(setParams(createFunction(), new double[] {-1.0, 0.0, 0.5, -0.5}), Color.orange);
        
        display.addFunction(setParams(createFunction(), new double[] {1.0, 0.0, 1.0, 0.0}), Color.blue);
//        display.addFunction(setParams(createFunction(), new double[] {1.0, 0.0, 0.5, 0.5}), Color.green);
//        display.addFunction(setParams(createFunction(), new double[] {1.0, 0.0, 0.5, -0.5}), Color.red);
        
//        display.addFunction(new SymmetricLeftSawtoothWaveFunction(), Color.blue);
//        display.addFunction(new SymmetricRightSawtoothWaveFunction(), Color.green);
//        display.addFunction(new SymmetricSqareWaveFunction(), Color.blue);
//        display.addFunction(new SymmetricTriangleWaveFunction(), Color.blue);

//        display.addFunction(new ExtendedCosFunction(), Color.red);
//        display.addFunction(new SymmetricSinFunction(), Color.green);
//        display.addFunction(new SymmetricTanhFunction(), Color.cyan);
//        display.addFunction(new SymmetricElliottFunction(), Color.red);
//        display.addFunction(new SymmetricGaussianFunction(), Color.green);
//        display.addFunction(new SymmetricSqrtFunction(), Color.orange);
//        display.addFunction(new AbsFunction(), Color.black);
    }
    
    public ActivationFunctionViewer() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display = new ActivationFunctionDisplay();
        setLayout(new BorderLayout());
        add(display, BorderLayout.CENTER);
        addFunctions();
    }

    public static void main(String[] args) {
        final ActivationFunctionViewer viewer = new ActivationFunctionViewer();
        viewer.setBounds(10, 10, 400, 400);
        viewer.setVisible(true);
    }

}

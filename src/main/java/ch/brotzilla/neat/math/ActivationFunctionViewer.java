package ch.brotzilla.neat.math;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ActivationFunctionViewer extends JFrame {

    private final ActivationFunctionDisplay display;
    
    private ActivationFunction createFunction() {
        return new ExtendedPeriodicCosFunction();
    }
    
    private ActivationFunctionWrapper wrap(ActivationFunction activationFunction, double... synapseDefaults) {
        if (synapseDefaults != null && synapseDefaults.length == 0) {
            synapseDefaults = null;
        }
        return new ActivationFunctionWrapper(activationFunction, synapseDefaults);
    }
    
    private void addFunctions() {
        display.addFunction(wrap(createFunction(), 0.0, 1.0, 0.0, 1.0, 0.0, 0.0), Color.blue);
//        display.addFunction(wrap(createFunction(), 2), Color.blue);
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

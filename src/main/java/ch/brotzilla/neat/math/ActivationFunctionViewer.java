package ch.brotzilla.neat.math;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class ActivationFunctionViewer extends JFrame {

    private final ActivationFunctionDisplay display;
    
    private void addFunctions() {
        display.addFunction(new SymmetricCosFunction(), Color.blue);
//        display.addFunction(new SymmetricTanhFunction(), Color.cyan);
        display.addFunction(new SymmetricElliottFunction(), Color.red);
        display.addFunction(new SymmetricGaussianFunction(), Color.green);
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

package ch.brotzilla.neat.math;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

@SuppressWarnings("serial")
public class ActivationFunctionDisplay extends JPanel {

    private final ActivationFunctionRenderer renderer;
    private final List<FunctionEntry> functionList, wrapperList;
    
    private class ResizeListener implements ComponentListener {

        public void componentResized(ComponentEvent e) {
            renderer.setSize(e.getComponent().getWidth(), e.getComponent().getHeight());
            renderFunctions();
        }

        public void componentMoved(ComponentEvent e) {}

        public void componentShown(ComponentEvent e) {}

        public void componentHidden(ComponentEvent e) {}
        
    }

    public static class FunctionEntry {
        
        private final ActivationFunction function;
        private Color color;
        private boolean active;
        
        public FunctionEntry(ActivationFunction function, Color color, boolean active) {
            Preconditions.checkNotNull(function, "The parameter 'function' must not be null");
            Preconditions.checkNotNull(color, "The parameter 'color' must not be null");
            this.function = function;
            this.color = color;
            this.active = active;
        }
        
        public ActivationFunction getActivationFunction() {
            return function;
        }
        
        public Color getColor() {
            return color;
        }
        
        public void setColor(Color value) {
            Preconditions.checkNotNull(value, "The parameter 'value' must not be null");
            color = value;
        }
        
        public boolean getActive() {
            return active;
        }
        
        public void setActive(boolean value) {
            active = value;
        }
        
    }
    
    public ActivationFunctionDisplay() {
        renderer = new ActivationFunctionRenderer();
        functionList = Lists.newArrayList();
        wrapperList = Collections.unmodifiableList(functionList);
        addComponentListener(new ResizeListener());
    }
    
    public List<FunctionEntry> getFunctions() {
        return wrapperList;
    }

    public FunctionEntry addFunction(ActivationFunction function, Color color) {
        Preconditions.checkNotNull(function, "The parameter 'function' must not be null");
        Preconditions.checkNotNull(color, "The parameter 'color' must not be null");
        final FunctionEntry entry = new FunctionEntry(function, color, true);
        functionList.add(entry);
        renderFunctions();
        return entry;
    }
    
    public FunctionEntry removeFunction(int index) {
        final FunctionEntry result = functionList.remove(index);
        renderFunctions();
        return result;
    }
    
    public void renderFunctions() {
        if (renderer.isReady()) {
            renderer.render(wrapperList);
            repaint();
        }
    }
    
    @Override
    public void paint(Graphics g) {
        if (renderer.isReady()) {
            g.drawImage(renderer.getBuffer(), 0, 0, null);
        } else {
            super.paint(g);
        }
    }
}

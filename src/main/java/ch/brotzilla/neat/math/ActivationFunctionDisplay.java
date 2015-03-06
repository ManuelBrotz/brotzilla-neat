package ch.brotzilla.neat.math;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
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
    
    private class MouseListener implements MouseWheelListener, MouseMotionListener, java.awt.event.MouseListener {

        private double sectionX, sectionY;
        private int startX, startY;
        private boolean move = false;
        
        public void mouseWheelMoved(MouseWheelEvent e) {
            final double delta = e.getPreciseWheelRotation();
            renderer.addZoomDelta(renderer.getSectionSize() * (e.isControlDown() ? 0.01 : 0.1) * delta);
            renderFunctions();
        }

        public void mousePressed(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) {
                sectionX = renderer.getSectionPosX();
                sectionY = renderer.getSectionPosY();
                startX = e.getX();
                startY = e.getY();
                move = true;
            }
        }

        public void mouseDragged(MouseEvent e) {
            if (move) {
                final int dx = e.getX() - startX, dy = e.getY() - startY;
                final double ddx = (double) dx / renderer.getGridWidth() * renderer.getSectionSize(), ddy = (double) dy / renderer.getGridHeight() * renderer.getSectionSize();
                renderer.setSectionPos(sectionX - ddx, sectionY - ddy);
                renderFunctions();
            }
        }

        public void mouseReleased(MouseEvent e) {
            move = false;
        }

        public void mouseMoved(MouseEvent e) {}

        public void mouseClicked(MouseEvent e) {}

        public void mouseEntered(MouseEvent e) {}

        public void mouseExited(MouseEvent e) {}
        
    }

    public static class FunctionEntry {
        
        private final ActivationFunctionWrapper function;
        private Color color;
        private boolean active;
        
        public FunctionEntry(ActivationFunctionWrapper function, Color color, boolean active) {
            Preconditions.checkNotNull(function, "The parameter 'function' must not be null");
            Preconditions.checkNotNull(color, "The parameter 'color' must not be null");
            this.function = function;
            this.color = color;
            this.active = active;
        }
        
        public ActivationFunctionWrapper getActivationFunction() {
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
        final MouseListener mouseListener = new MouseListener();
        addMouseWheelListener(mouseListener);
        addMouseMotionListener(mouseListener);
        addMouseListener(mouseListener);
    }
    
    public List<FunctionEntry> getFunctions() {
        return wrapperList;
    }

    public FunctionEntry addFunction(ActivationFunctionWrapper function, Color color) {
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

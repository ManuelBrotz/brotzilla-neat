package ch.brotzilla.neat.math;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.List;

import com.google.common.base.Preconditions;

import ch.brotzilla.neat.math.ActivationFunctionDisplay.FunctionEntry;

public class ActivationFunctionRenderer {

    private static final BasicStroke Stroke1 = new BasicStroke(1), Stroke2 = new BasicStroke(2);
    
    private final Screen screen;
    private BufferedImage buffer;
    private Graphics2D g;
    
    private static class Screen {
        
        private int border, x, y, width, height, gridWidth, gridHeight;
        private boolean render;
        
        private double sectionX, sectionY, sectionSize, stepX;
        
        public Screen(int border, int width, int height, double beginX, double beginY, double sectionSize) {
            Preconditions.checkArgument(border >= 0, "The parameter 'border' has to be greater than or equal to zero");
            Preconditions.checkArgument(width >= 0, "The parameter 'width' has to be greater than or equal to zero");
            Preconditions.checkArgument(height >= 0, "The parameter 'height' has to be greater than or equal to zero");
            Preconditions.checkArgument(sectionSize > 0.0, "The parameter 'sectionSize' has to be greater than zero");
            this.border = border;
            x = border;
            y = border;
            this.width = width;
            this.height = height;
            gridWidth = Math.max(0, width - 2 * border);
            gridHeight = Math.max(0, height - 2 * border);
            render = (gridWidth >= 20) && (gridHeight >= 20);
            this.sectionX = beginX;
            this.sectionY = beginY;
            this.sectionSize = sectionSize;
            if (render) {
                stepX = 1.0d / gridWidth;
            }
        }

        public void setBorder(int value) {
            Preconditions.checkArgument(value >= 0, "The parameter 'value' has to be greater than or equal to zero");
            border = value;
            x = border;
            y = border;
            gridWidth = Math.max(0, width - 2 * border);
            gridHeight = Math.max(0, height - 2 * border);
            render = (gridWidth >= 20) && (gridHeight >= 20);
            if (render) {
                stepX = 1.0d / gridWidth;
            }
        }
        
        public void setSize(int width, int height) {
            Preconditions.checkArgument(width >= 0, "The parameter 'width' has to be greater than or equal to zero");
            Preconditions.checkArgument(height >= 0, "The parameter 'height' has to be greater than or equal to zero");
            this.width = width;
            this.height = height;
            gridWidth = Math.max(0, width - 2 * border);
            gridHeight = Math.max(0, height - 2 * border);
            render = (gridWidth >= 20) && (gridHeight >= 20);
            if (render) {
                stepX = 1.0d / gridWidth;
            }
        }
        
        public int tx(double v) {
            return x + (int) Math.round(gridWidth * (v - sectionX) / sectionSize);
        }
        
        public int ty(double v) {
            return y + (int) Math.round(gridHeight * (v - sectionY) / sectionSize);
        }
        
    }
    
    private void renderLine(double x1, double y1, double x2, double y2) {
        g.drawLine(screen.tx(x1), screen.ty(y1), screen.tx(x2), screen.ty(y2));
    }
    
    private void clearScreen() {
        g.setBackground(Color.white);
        g.clearRect(0, 0, screen.width, screen.height);
    }
    
    private double trunc(double v, double stepSize) {
        double tmp = v * (1.0 / stepSize);
        if (tmp > 0) {
            return Math.floor(tmp) * stepSize;
        }
        if (tmp < 0) {
            return Math.ceil(tmp) * stepSize;
        }
        return tmp;
    }
    
    private void renderGrid(Color color, double stepSize) {
        g.setStroke(Stroke1);
        g.setColor(color);
        final int steps = (int) Math.round(screen.sectionSize / stepSize) + 1;
        final double beginX = trunc(screen.sectionX, stepSize);
        for (int i = 0; i < steps; i++) {
            final double x = beginX + (stepSize * i);
            if (x < screen.sectionX) continue;
            if (x > screen.sectionX + screen.sectionSize) break;
            renderLine(x, screen.sectionY, x, screen.sectionY + screen.sectionSize);
        }
        final double beginY = trunc(screen.sectionY, stepSize);
        for (int i = 0; i < steps; i++) {
            final double y = beginY + (stepSize * i);
            if (y < screen.sectionY) continue;
            if (y > screen.sectionY + screen.sectionSize) break;
            renderLine(screen.sectionX, y, screen.sectionX + screen.sectionSize, y);
        }
    }
    
    private void renderFunctions(List<FunctionEntry> functions) {
        g.setStroke(Stroke2);
        g.setClip(screen.x, screen.y, screen.gridWidth, screen.gridHeight);
        for (final FunctionEntry entry : functions) {
            if (!entry.getActive()) {
                continue;
            }
            final ActivationFunction f = entry.getActivationFunction();
            double x = screen.sectionX + screen.stepX, xprev = screen.sectionX, yprev = -f.compute(screen.sectionX);
            g.setColor(entry.getColor());
            while (x < screen.sectionX + screen.sectionSize) {
                final double y = -f.compute(x);
                renderLine(xprev, yprev, x, y);
                xprev = x;
                yprev = y;
                x += screen.stepX;
            }
        }
        g.setClip(0, 0, screen.width, screen.height);
    }
    
    public ActivationFunctionRenderer() {
        screen = new Screen(30, 0, 0, -1.0, -1.0, 2.0);
    }
    
    public boolean isReady() {
        return buffer != null;
    }
    
    public BufferedImage getBuffer() {
        return buffer;
    }
    
    public int getBorder() {
        return screen.border;
    }
    
    public void setBorder(int value) {
        screen.setBorder(value);
    }
    
    public int getWidth() {
        return screen.width;
    }
    
    public int getHeight() {
        return screen.height;
    }
    
    public void setSize(int width, int height) {
        if (width == 0 || height == 0) {
            screen.setSize(0, 0);
            buffer = null;
            g = null;
        } else if (buffer == null || buffer.getWidth() != width || buffer.getHeight() != height) {
            screen.setSize(width, height);
            buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            g = (Graphics2D) buffer.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
    }
    
    public int getGridWidth() {
        return screen.gridWidth;
    }
    
    public int getGridHeight() {
        return screen.gridHeight;
    }
    
    public double getSectionPosX() {
        return screen.sectionX;
    }
    
    public double getSectionPosY() {
        return screen.sectionY;
    }
    
    public void setSectionPos(double x, double y) {
        screen.sectionX = x;
        screen.sectionY = y;
    }

    public void addSectionPosDelta(double dx, double dy) {
        setSectionPos(screen.sectionX + dx, screen.sectionY + dy);
    }

    public void addSectionPosDelta(double delta) {
        setSectionPos(screen.sectionX + delta, screen.sectionY + delta);
    }
    
    public double getSectionSize() {
        return screen.sectionSize;
    }

    public void setSectionSize(double value) {
        value = Math.max(0.1, value);
        screen.sectionSize = value;
    }
    
    public void addSectionSizeDelta(double delta) {
        setSectionSize(screen.sectionSize + delta);
    }
    
    public void addZoomDelta(double delta) {
        addSectionPosDelta(-delta / 4);
        addSectionSizeDelta(delta / 2);
    }
    
    public void resetSection() {
        screen.sectionX = -1.0;
        screen.sectionY = -1.0;
        screen.sectionSize = 2.0;
    }

    public void render(List<FunctionEntry> functions) {
        Preconditions.checkState(buffer != null, "The renderer is not ready");
        clearScreen();
        if (screen.render) {
            renderGrid(Color.lightGray, 0.1);
            renderGrid(Color.gray, 1.0);
            renderFunctions(functions);
        }
    }
    
}

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
    
    private BufferedImage buffer;
    private Graphics2D g;
    private Screen screen;
    private double zoomFactor = 1.05;
    
    private static class Screen {
        
        public final int x, y, displayWidth, displayHeight, screenWidth, screenHeight, halfScreenWidth, halfScreenHeight;
        public final boolean render;
        
        public double zoomFactor, stepX, begin, end;
        
        public Screen(int width, int height, int border, double zoom) {
            Preconditions.checkArgument(width >= 0, "The parameter 'width' has to be greater than or equal to zero");
            Preconditions.checkArgument(height >= 0, "The parameter 'height' has to be greater than or equal to zero");
            Preconditions.checkArgument(border >= 0, "The parameter 'border' has to be greater than or equal to zero");
            x = border;
            y = border;
            displayWidth = width;
            displayHeight = height;
            screenWidth = Math.max(0, width - 2 * border);
            screenHeight = Math.max(0, height - 2 * border);
            halfScreenWidth = screenWidth / 2;
            halfScreenHeight = screenHeight / 2;
            render = (screenWidth >= 20) && (screenHeight >= 20);
            setZoomFactor(zoom);
        }
        
        public void setZoomFactor(double value) {
            value = Math.max(0.1, value);
            zoomFactor = value;
            stepX = 1.0d / screenWidth * value;
            begin = -value;
            end = value;
        }

        public int tx(double v) {
            return x + halfScreenWidth + (int) Math.round(halfScreenWidth * v / zoomFactor);
        }
        
        public int ty(double v) {
            return y + halfScreenHeight + (int) Math.round(halfScreenHeight * v / zoomFactor);
        }
        
    }
    
    private void renderLine(double x1, double y1, double x2, double y2) {
        g.drawLine(screen.tx(x1), screen.ty(y1), screen.tx(x2), screen.ty(y2));
    }
    
    private void clearScreen() {
        g.setBackground(Color.white);
        g.clearRect(0, 0, screen.displayWidth, screen.displayHeight);
    }
    
    private void renderLargeGrid() {
        g.setStroke(Stroke1);
        final int begin = (int) screen.begin, end = (int) screen.end;
        for (int x = begin; x <= end; x++) {
            g.setColor(Color.gray);
            renderLine(x, screen.begin, x, screen.end);
        }
        for (int y = begin; y <= end; y++) {
            g.setColor(Color.gray);
            renderLine(screen.begin, y, screen.end, y);
        }
    }
    
    private void renderSmallGrid() {
        g.setStroke(Stroke1);
        final int begin = (int) (screen.begin * 10), end = (int) (screen.end * 10);
        for (int x = begin; x <= end; x++) {
            if (x % 10 == 0) continue;
            g.setColor(Color.lightGray);
            renderLine(x * 0.1, screen.begin, x * 0.1, screen.end);
        }
        for (int y = begin; y <= end; y++) {
            if (y % 10 == 0) continue;
            g.setColor(Color.lightGray);
            renderLine(screen.begin, y * 0.1, screen.end, y * 0.1);
        }
    }
    
    private void renderFunctions(List<FunctionEntry> functions) {
        g.setStroke(Stroke2);
        g.setClip(screen.x, screen.y, screen.displayWidth, screen.displayHeight);
        for (final FunctionEntry entry : functions) {
            if (!entry.getActive()) {
                continue;
            }
            final ActivationFunction f = entry.getActivationFunction();
            double x = screen.begin + screen.stepX, xprev = screen.begin, yprev = -f.compute(screen.begin);
            g.setColor(entry.getColor());
            while (x < screen.end) {
                final double y = -f.compute(x);
                renderLine(xprev, yprev, x, y);
                xprev = x;
                yprev = y;
                x += screen.stepX;
            }
        }
        g.setClip(0, 0, screen.displayWidth, screen.displayHeight);
    }
    
    public ActivationFunctionRenderer() {}
    
    public boolean isReady() {
        return buffer != null;
    }
    
    public BufferedImage getBuffer() {
        return buffer;
    }
    
    public int getWidth() {
        return buffer == null ? 0 : buffer.getWidth();
    }
    
    public int getHeight() {
        return buffer == null ? 0 : buffer.getHeight();
    }
    
    public void setSize(int width, int height) {
        if (buffer == null || buffer.getWidth() != width || buffer.getHeight() != height) {
            buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            g = (Graphics2D) buffer.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            screen = new Screen(width, height, 30, zoomFactor);
        }
    }
    
    public double getZoomFactor() {
        return zoomFactor;
    }
    
    public void setZoomFactor(double value) {
        this.zoomFactor = value;
        if (screen != null) {
            screen.setZoomFactor(value);
        }
    }

    public void render(List<FunctionEntry> functions) {
        Preconditions.checkState(buffer != null, "The renderer is not ready");
        clearScreen();
        if (screen != null && screen.render) {
            renderSmallGrid();
            renderLargeGrid();
            renderFunctions(functions);
        }
    }
    
}

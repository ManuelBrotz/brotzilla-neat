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

    private BufferedImage buffer;
    private Graphics2D g;
    
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
            g.setStroke(new BasicStroke(2));
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        }
    }

    public void render(List<FunctionEntry> functions) {
        Preconditions.checkState(buffer != null, "The renderer is not ready");
        final int w = getWidth(), h = getHeight();
        final int wh = w / 2, hh = h / 2;
        final double xstep = 1.0d / w;
        g.setBackground(Color.white);
        g.clearRect(0, 0, w, h);
        for (final FunctionEntry entry : functions) {
            if (!entry.getActive()) {
                continue;
            }
            final ActivationFunction f = entry.getActivationFunction();
            int xprev = (int) Math.round(wh * -1.0d), yprev = (int) Math.round(hh * -f.compute(-1.0d));
            double x = -1.0d + xstep;
            g.setColor(entry.getColor());
            while (x < 1.0d) {
                final int xx = (int) Math.round(wh * x), yy = (int) Math.round(hh * -f.compute(x));
                g.drawLine(xprev + wh, yprev + hh, xx + wh, yy + hh);
                xprev = xx;
                yprev = yy;
                x += xstep;
            }
        }
    }
    
}

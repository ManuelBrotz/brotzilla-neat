package ch.brotzilla.cppns.images;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import ch.brotzilla.cppns.patterns.PatternGenerator;

import com.google.common.base.Preconditions;

public class ImageGenerator {

    private final ImageType type;
    private final Section section;
    
    private PatternGenerator generator;

    private BufferedImage image;
    private WritableRaster raster;
    private int[] colorPixels;
    private byte[] grayPixels;
    private short[] grayHDPixels;
    
    private void allocateImage(int width, int height) {
        Preconditions.checkArgument(width > 0, "The parameter 'width' has to be greater than zero");
        Preconditions.checkArgument(height > 0, "The parameter 'height' has to be greater than zero");
        image = new BufferedImage(width, height, type.getBufferedImageType());
        raster = image.getRaster();
        colorPixels = null;
        grayPixels = null;
        grayHDPixels = null;
        switch (type) {
        case Gray:
            grayPixels = (byte[]) raster.getDataElements(0, 0, width, height, null);
            break;
        case GrayHighDefinition:
            grayHDPixels = (short[]) raster.getDataElements(0, 0, width, height, null);
            break;
        default: 
            colorPixels = (int[]) raster.getDataElements(0, 0, width, height, null);
        }
    }
    
    private int scale(double value) {
        final int result = value < 0 ? (int) (-value * 255) : (int) (value * 255);
        if (result > 255) return 255;
        return result;
    }
    
    private int scaleHD(double value) {
        final int result = value < 0 ? (int) (-value * 65535) : (int) (value * 65535);
        if (result > 65535) return 65535;
        return result;
    }
    
    private int encodeColorAlphaPixel(double a, double r, double g, double b) {
        return (scale(a) << 24) | (scale(r) << 16) | (scale(g) << 8) | scale(b);
    }

    private int encodeColorPixel(double r, double g, double b) {
        return 0xFF000000 | (scale(r) << 16) | (scale(g) << 8) | scale(b);
    }

    private int encodeGrayAlphaPixel(double a, double v) {
        final int vv = scale(v);
        return (scale(a) << 24) | (vv << 16) | (vv << 8) | vv;
    }
    
    private short encodeGrayHDPixel(double v) {
        return (short) (scaleHD(v) & 0xFFFF);
    }
    
    private byte encodeGrayPixel(double v) {
        return (byte) (scale(v) & 0xFF);
    }

    private void generateColorAlphaImage() {
        final double[] output = new double[4];
        final int w = getWidth(), h = getHeight();
        final double stepX = section.getWidth() / w, stepY = section.getHeight() / h;
        for (int y = 0; y < h; y++) {
            final double yy = section.getY() + (stepY * y);
            for (int x = 0; x < w; x++) {
                final double xx = section.getX() + (stepX * x);
                final int index = y * w + x;
                generator.generate(xx, yy, output);
                colorPixels[index] = encodeColorAlphaPixel(output[0], output[1], output[2], output[3]);
            }
        }
        raster.setDataElements(0, 0, w, h, colorPixels);
    }

    private void generateColorImage() {
        final double[] output = new double[3];
        final int w = getWidth(), h = getHeight();
        final double stepX = section.getWidth() / w, stepY = section.getHeight() / h;
        for (int y = 0; y < h; y++) {
            final double yy = section.getY() + (stepY * y);
            for (int x = 0; x < w; x++) {
                final double xx = section.getX() + (stepX * x);
                final int index = y * w + x;
                generator.generate(xx, yy, output);
                colorPixels[index] = encodeColorPixel(output[0], output[1], output[2]);
            }
        }
        raster.setDataElements(0, 0, w, h, colorPixels);
    }

    private void generateHighDefinitionGrayImage() {
        final double[] output = new double[1];
        final int w = getWidth(), h = getHeight();
        final double stepX = section.getWidth() / w, stepY = section.getHeight() / h;
        for (int y = 0; y < h; y++) {
            final double yy = section.getY() + (stepY * y);
            for (int x = 0; x < w; x++) {
                final double xx = section.getX() + (stepX * x);
                final int index = y * w + x;
                generator.generate(xx, yy, output);
                grayHDPixels[index] = encodeGrayHDPixel(output[0]);
            }
        }
        raster.setDataElements(0, 0, w, h, grayHDPixels);
    }

    private void generateGrayAlphaImage() {
        final double[] output = new double[2];
        final int w = getWidth(), h = getHeight();
        final double stepX = section.getWidth() / w, stepY = section.getHeight() / h;
        for (int y = 0; y < h; y++) {
            final double yy = section.getY() + (stepY * y);
            for (int x = 0; x < w; x++) {
                final double xx = section.getX() + (stepX * x);
                final int index = y * w + x;
                generator.generate(xx, yy, output);
                colorPixels[index] = encodeGrayAlphaPixel(output[0], output[1]);
            }
        }
        raster.setDataElements(0, 0, w, h, colorPixels);
    }

    private void generateGrayImage() {
        final double[] output = new double[1];
        final int w = getWidth(), h = getHeight();
        final double stepX = section.getWidth() / w, stepY = section.getHeight() / h;
        for (int y = 0; y < h; y++) {
            final double yy = section.getY() + (stepY * y);
            for (int x = 0; x < w; x++) {
                final double xx = section.getX() + (stepX * x);
                final int index = y * w + x;
                generator.generate(xx, yy, output);
                grayPixels[index] = encodeGrayPixel(output[0]);
            }
        }
        raster.setDataElements(0, 0, w, h, grayPixels);
    }

    public ImageGenerator(int width, int height, ImageType type, PatternGenerator generator) {
        Preconditions.checkNotNull(type, "The parameter 'type' must not be null");
        Preconditions.checkNotNull(generator, "The parameter 'generator' must not be null");
        Preconditions.checkArgument(generator.getOutputSize() == type.getPatternGeneratorOutputSize(), "The output size of the parameter 'generator' has to be equal to " + type.getPatternGeneratorOutputSize());
        this.type = type;
        this.section = new Section(-1, -1, 2, 2);
        this.generator = generator;
        allocateImage(width, height);
    }
    
    public ImageType getType() {
        return type;
    }
    
    public Section getSection() {
        return section;
    }
    
    public PatternGenerator getPatternGenerator() {
        return generator;
    }
    
    public BufferedImage getImage() {
        return image;
    }
    
    public int getWidth() {
        return image.getWidth();
    }
    
    public int getHeight() {
        return image.getHeight();
    }
    
    public void setSize(int width, int height) {
        if (width != image.getWidth() || height != image.getHeight()) {
            allocateImage(width, height);
        }
    }
    
    public void generate() {
        switch (type) {
        case Gray:
            generateGrayImage();
            break;
        case GrayAlpha:
            generateGrayAlphaImage();
            break;
        case GrayHighDefinition:
            generateHighDefinitionGrayImage();
            break;
        case Color:
            generateColorImage();
            break;
        case ColorAlpha:
            generateColorAlphaImage();
            break;
        default: 
            throw new UnsupportedOperationException();
        }
    }
    
}

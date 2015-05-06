package ch.brotzilla.patterns;

import java.awt.image.BufferedImage;

public enum ImageType {
    
    Gray {
        @Override
        public int getBufferedImageType() {
            return BufferedImage.TYPE_BYTE_GRAY;
        }

        @Override
        public int getPatternGeneratorOutputSize() {
            return 1;
        }
    }, 
    
    GrayAlpha {
        @Override
        public int getBufferedImageType() {
            return BufferedImage.TYPE_INT_ARGB;
        }

        @Override
        public int getPatternGeneratorOutputSize() {
            return 2;
        }
    }, 
    
    GrayHighDefinition {
        @Override
        public int getBufferedImageType() {
            return BufferedImage.TYPE_USHORT_GRAY;
        }

        @Override
        public int getPatternGeneratorOutputSize() {
            return 1;
        }
    }, 
    
    Color {
        @Override
        public int getBufferedImageType() {
            return BufferedImage.TYPE_INT_ARGB;
        }

        @Override
        public int getPatternGeneratorOutputSize() {
            return 3;
        }
    }, 
    
    ColorAlpha {
        @Override
        public int getBufferedImageType() {
            return BufferedImage.TYPE_INT_ARGB;
        }

        @Override
        public int getPatternGeneratorOutputSize() {
            return 4;
        }
    };
    
    public abstract int getBufferedImageType();
    
    public abstract int getPatternGeneratorOutputSize();
    
}
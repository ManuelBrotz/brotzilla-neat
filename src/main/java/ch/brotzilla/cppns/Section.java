package ch.brotzilla.cppns;

import com.google.common.base.Preconditions;

public class Section {

    private double x = -1, y = -1, width = 2, height = 2;
    
    public Section(double x, double y, double width, double height) {
        Preconditions.checkArgument(width > 0, "The parameter 'width' has to be greater than zero");
        Preconditions.checkArgument(height > 0, "The parameter 'height' has to be greater than zero");
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public Section(Section source) {
        Preconditions.checkNotNull(source, "The parameter 'source' must not be null");
        x = source.x;
        y = source.y;
        width = source.width;
        height = source.height;
    }

    public double getX() {
        return x; 
    }
    
    public void setX(double value) {
        x = value;
    }
    
    public double getY() {
        return y;
    }
    
    public void setY(double value) {
        y = value;
    }
    
    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setPosition(Section value) {
        Preconditions.checkNotNull(value, "The parameter 'value' must not be null");
        x = value.x;
        y = value.y;
    }

    public double getWidth() {
        return width;
    }
    
    public void setWidth(double value) {
        Preconditions.checkArgument(value > 0, "The parameter 'value' has to be greater than zero");
        width = value;
    }
    
    public double getHeight() {
        return height;
    }
    
    public void setHeight(double value) {
        Preconditions.checkArgument(value > 0, "The parameter 'value' has to be greater than zero");
        height = value;
    }
    
    public void setSize(double width, double height) {
        Preconditions.checkArgument(width > 0, "The parameter 'width' has to be greater than zero");
        Preconditions.checkArgument(height > 0, "The parameter 'height' has to be greater than zero");
        this.width = width;
        this.height = height;
    }
    
    public void setSize(Section value) {
        Preconditions.checkNotNull(value, "The parameter 'value' must not be null");
        width = value.width;
        height = value.height;
    }
    
    public void setBounds(double x, double y, double width, double height) {
        Preconditions.checkArgument(width > 0, "The parameter 'width' has to be greater than zero");
        Preconditions.checkArgument(height > 0, "The parameter 'height' has to be greater than zero");
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void setBounds(Section value) {
        Preconditions.checkNotNull(value, "The parameter 'value' must not be null");
        x = value.x;
        y = value.y;
        width = value.width;
        height = value.height;
    }

}

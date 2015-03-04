package ch.brotzilla.neat.math;

import com.google.common.base.Preconditions;

public class ActivationFunctionParameter {

    private String name, description;
    private double defaultValue, viewerLowerBound, viewerUpperBound;
    
    private ActivationFunctionParameter() {}
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public double getDefaultValue() {
        return defaultValue;
    }
    
    public double getViewerLowerBound() {
        return viewerLowerBound;
    }
    
    public double getViewerUpperBound() {
        return viewerUpperBound;
    }
    
    @Override
    public ActivationFunctionParameter clone() {
        final ActivationFunctionParameter p = new ActivationFunctionParameter();
        p.name = name;
        p.description = description;
        p.defaultValue = defaultValue;
        p.viewerLowerBound = viewerLowerBound;
        p.viewerUpperBound = viewerUpperBound;
        return p;
    }
    
    public static class Builder {
        
        private final ActivationFunctionParameter p;
        
        public Builder() {
            p = new ActivationFunctionParameter();
        }
        
        public Builder setName(String value) {
            Preconditions.checkNotNull(value, "The parameter 'value' must not be null");
            Preconditions.checkArgument(!value.trim().isEmpty(), "The parameter 'value' must not be empty");
            p.name = value;
            return this;
        }
        
        public Builder setDescription(String value) {
            Preconditions.checkNotNull(value, "The parameter 'value' must not be null");
            Preconditions.checkArgument(!value.trim().isEmpty(), "The parameter 'value' must not be empty");
            p.description = value;
            return this;
        }
        
        public Builder setDefaultValue(double value) {
            Preconditions.checkState(!Double.isInfinite(value) && !Double.isNaN(value), "The parameter 'value' has to be a valid double value (neither infinity nor nan are allowed)");
            p.defaultValue = value;
            return this;
        }
        
        public Builder setViewerLowerBound(double value) {
            Preconditions.checkState(!Double.isInfinite(value) && !Double.isNaN(value), "The parameter 'value' has to be a valid double value (neither infinity nor nan are allowed)");
            p.viewerLowerBound = value;
            return this;
        }
        
        public Builder setViewerUpperBound(double value) {
            Preconditions.checkState(!Double.isInfinite(value) && !Double.isNaN(value), "The parameter 'value' has to be a valid double value (neither infinity nor nan are allowed)");
            p.viewerUpperBound = value;
            return this;
        }
        
        public ActivationFunctionParameter build() {
            Preconditions.checkState(p.name != null, "The property 'name' must not be null");
            Preconditions.checkState(!p.name.trim().isEmpty(), "The property 'name' must not be empty");
            Preconditions.checkState(p.description != null, "The property 'description' must not be null");
            Preconditions.checkState(!p.description.trim().isEmpty(), "The property 'description' must not be empty");
            Preconditions.checkState(!Double.isInfinite(p.defaultValue) && !Double.isNaN(p.defaultValue), "The property 'defaultValue' has to be a valid double value (neither infinity nor nan are allowed)");
            Preconditions.checkState(!Double.isInfinite(p.viewerLowerBound) && !Double.isNaN(p.viewerLowerBound), "The property 'viewerLowerBound' has to be a valid double value (neither infinity nor nan are allowed)");
            Preconditions.checkState(!Double.isInfinite(p.viewerUpperBound) && !Double.isNaN(p.viewerUpperBound), "The property 'viewerUpperBound' has to be a valid double value (neither infinity nor nan are allowed)");
            Preconditions.checkState(p.viewerLowerBound < p.viewerUpperBound, "The property 'viewerLowerBound' has to be less than the property 'viewerUpperBound'");
            return p.clone();
        }
        
    }

}

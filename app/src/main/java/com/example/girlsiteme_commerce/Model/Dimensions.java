package com.example.girlsiteme_commerce.Model;

import java.util.LinkedHashMap;
import java.util.Map;

public class Dimensions {
    private Double width;
    private Double height;
    private Double depth;
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getDepth() {
        return depth;
    }

    public void setDepth(Double depth) {
        this.depth = depth;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}

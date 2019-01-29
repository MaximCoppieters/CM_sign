package be.pxl.data.domain;

import java.awt.*;

public class SignDimensions {
    private Point origin;
    private double width;
    private double height;

    public SignDimensions(Point origin, double width, double height) {
        this.origin = origin;
        this.width = width;
        this.height = height;
    }

    public Point getOrigin() {
        return origin;
    }

    public void setOrigin(Point origin) {
        this.origin = origin;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}

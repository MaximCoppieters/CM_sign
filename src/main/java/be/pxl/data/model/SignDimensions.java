package be.pxl.data.model;

import java.awt.*;

/**
 * Objects of SignDimensions hold the location within the document the invitee can sign at.
 * The origin is a Point that holds the position at which the signing area starts.
 * This consists of an x and y-coordinate ranging from 0.0 to 1.0. (start to end of document)
 * The width and height describe the area of the signing location, measured in centimeters.
 */
public class SignDimensions {
    private Point origin;
    private int width;
    private int height;

    public SignDimensions(Point origin, int width, int height) {
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}

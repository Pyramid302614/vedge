package org.py.vedge;

import javafx.scene.canvas.GraphicsContext;

import java.util.Arrays;

public class Graphics implements GraphicsAdapter {

    private final GraphicsContext gc;

    private Color fill = Color.ORANGE;
    private Color stroke = Color.BLACK;

    public void fill(int r, int g, int b, int a) {
        fill(new Color(r,g,b,a));
    }
    public void fill(int r, int g, int b) {
        fill(new Color(r,g,b));
    }
    public void fill(Color color) {
        fill = color;
        gc.setFill(color.toColorFX());
    }
    public void stroke(Color color) {
        stroke = color;
        gc.setStroke(color.toColorFX());
    }
    public void strokeWeight(double weight) {
        gc.setLineWidth(weight);
    }

    public void rect(double x, double y, double w, double h) {
        if(fill.alpha() != 0) gc.fillRect(x,y,w,h);
        if(stroke.alpha() != 0) gc.strokeRect(x,y,w,h);
    }

    public void ellipse(double x, double y, double w, double h) {
        if(fill.alpha() != 0) gc.fillOval(x,y,w,h);
        if(stroke.alpha() != 0) gc.fillOval(x,y,w,h);
    }

    public void image(Image image, double x, double y, double w, double h) {

    }

    public void polygon(double[] allX, double[] allY) {
        if(fill.alpha() != 0) gc.fillPolygon(allX,allY,allX.length);
        if(stroke.alpha() != 0) gc.strokePolygon(allX,allY,allX.length);
    }

    public Graphics(GraphicsContext gc) {

        this.gc = gc;

    }

}

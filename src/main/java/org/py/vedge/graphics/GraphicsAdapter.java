package org.py.vedge.graphics;

public abstract class GraphicsAdapter {

    public Color fill = Color.ORANGE;
    public Color stroke = Color.BLACK;
    public double strokeWeight = 2;

    public void fill(Color color) {
        fill = color;
    }
    public void stroke(Color color) {
        stroke = color;
    }
    public void fill(int r, int g, int b) {
        fill = new Color(r,g,b);
    }
    public void stroke(int r, int g, int b) {
        stroke = new Color(r,g,b);
    }
    public void fill(int r, int g, int b, int a) {
        fill = new Color(r,g,b,a);
    }
    public void stroke(int r, int g, int b, int a) {
        stroke = new Color(r,g,b,a);
    }
    public void strokeWeight(int w) {
        strokeWeight = w;
    }

    public final void noFill() {
        fill(Color.BLANK);
    }
    public final void noStroke() {
        stroke(Color.BLANK);
    }

    public final void fillNoStroke(Color color) {
        fill(color);
        noStroke();
    }
    public final void strokeNoFill(Color color) {
        stroke(color);
        noFill();
    }
    public final void fillNoStroke(int r, int g, int b) {
        fill(r,g,b);
        noStroke();
    }
    public final void strokeNoFill(int r, int g, int b) {
        stroke(r,g,b);
        noFill();
    }
    public final void fillNoStroke(int r, int g, int b, int a) {
        fill(r,g,b,a);
        noStroke();
    }
    public final void strokeNoFill(int r, int g, int b, int a) {
        stroke(r,g,b,a);
        noFill();
    }

    public abstract void rect(double x, double y, double w, double h);
    public abstract void ellipse(double x, double y, double w, double h);

    public abstract void polygon(double[] allX, double[] allY);

//    public abstract void image(Image image, double x, double y, double w, double h);

    public abstract void textSize(int s);
    public abstract void textColor(Color color);
    public abstract void textAttributes(int s, Color color);
    public abstract void text(String text, double x, double y);
    public abstract double textWidth(String s);
    public abstract double textAscent();
    public abstract double textHeight();

}

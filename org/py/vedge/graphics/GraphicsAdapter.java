package org.py.vedge.graphics;

public interface GraphicsAdapter {

    void fill(Color color);
    void stroke(Color color);

    default void noFill() {
        fill(Color.BLANK);
    }
    default void noStroke() {
        stroke(Color.BLANK);
    }

    void rect(double x, double y, double w, double h);
    void ellipse(double x, double y, double rw, double rh);

    void polygon(double[] allX, double[] allY);

    void image(Image image, double x, double y, double w, double h);

    void textSize(double s);
    void text(String text, double x, double y);

    default void fillNoStroke(Color color) {
        fill(color);
        noStroke();
    }
    default void strokeNoFill(Color color) {
        stroke(color);
        noFill();
    }

}

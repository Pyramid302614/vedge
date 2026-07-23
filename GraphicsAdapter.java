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

    void polyStart();
    void vertex(double x, double y);
    void polyEnd();

    void image(Image image, double x, double y, double w, double h);

    default void fillNoStroke(Color color) {
        fill(color);
        noStroke();
    }
    default void strokeNoFill(Color color) {
        stroke(color);
        noFill();
    }

}

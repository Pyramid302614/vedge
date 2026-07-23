import javafx.scene.canvas.GraphicsContext;

public class Graphics implements GraphicsAdapter {

    private final GraphicsContext gc;

    private Color fill;
    private Color stroke;

    public void fill(Color color) {
        fill = color;
        gc.setFill(color.toColorFX());
    }
    public void stroke(Color color) {
        stroke = color;
        gc.setStroke(color.toColorFX());
    }

    public void rect(double x, double y, double w, double h) {
        if(stroke.alpha() == 0) {
            gc.fillRect(x,y,w,h);
        } else if(fill.alpha() == 0) {
            gc.strokeRect(x,y,w,h);
        } else {
            gc.rect(x,y,w,h);
        }
    }

    public void ellipse(double x, double y, double w, double h) {
        if(stroke.alpha() != 0) {
            gc.strokeOval(x,y,w,h);
        } else if(fill.alpha() != 0) {
            gc.fillOval(x,y,w,h);
        }
    }

    public void image(Image image, double x, double y, double w, double h) {

    }

    public void polyStart() {}
    public void vertex(double x, double y) {}
    public void polyEnd() {}

    public Graphics(GraphicsContext gc) {

        this.gc = gc;

    }

}

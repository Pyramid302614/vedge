package org.py.vedge.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;

public class Graphics implements GraphicsAdapter {

    public final GraphicsContext gc;

    public Color fill = Color.ORANGE;
    public Color stroke = Color.BLACK;

    public double textSize = 12;
    public FontPosture textPosture = FontPosture.REGULAR;
    public String textFamily = "Arial";

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
        if(stroke.alpha() != 0) gc.strokeOval(x,y,w,h);
    }

    public void image(Image image, double x, double y, double w, double h) {

    }

    public void text(String text, double x, double y) {
        if(fill.alpha() != 0) gc.fillText(text,x,y);
        if(stroke.alpha() != 0) gc.strokeText(text,x,y);
    }

    public void updateFont() {
        gc.setFont(Font.font(textFamily,textPosture,textSize));
    }
    public void textSize(double s) {
        textSize = s;
        updateFont();
    }
    public void textPosture(FontPosture fontPosture) {
        textPosture = fontPosture;
        updateFont();
    }
    public void textFamily(String family) {
        textFamily = family;
        updateFont();
    }

    public double textWidth(String s) {
        Text text = new Text(s);
        text.setFont(gc.getFont());
        return text.getLayoutBounds().getWidth();
    }
    public double textAscent() {
        Text text = new Text("H");
        text.setFont(gc.getFont());
        return text.getBaselineOffset();
    }
    public double textHeight() {
        Text text = new Text("Hg");
        text.setFont(gc.getFont());
        return text.getBoundsInLocal().getHeight();
    }

    public void polygon(double[] allX, double[] allY) {
        if(fill.alpha() != 0) gc.fillPolygon(allX,allY,allX.length);
        if(stroke.alpha() != 0) gc.strokePolygon(allX,allY,allX.length);
    }

    public Graphics(GraphicsContext gc) {

        this.gc = gc;

    }

}

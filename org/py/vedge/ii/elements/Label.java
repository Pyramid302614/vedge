package org.py.vedge.ii.elements;

import javafx.scene.text.FontPosture;
import org.py.vedge.graphics.Color;
import org.py.vedge.graphics.ModifiedGraphics;
import org.py.vedge.ii.Bounds;
import org.py.vedge.ii.Dimension;

public class Label extends Element {

    public String text;

    public void text(String text) {
        this.text = text;
    }

    private Bounds bounds;

    public String family = "Arial";
    public FontPosture posture = FontPosture.REGULAR;
    public double size = 12;
    public Color color = Color.BLACK;

    public Label font(String family, FontPosture posture, double size, Color color) {
        this.family = family;
        this.posture = posture;
        this.size = size;
        this.color = color;
        return this;
    }

    public Label(String text) {
        this.text = text;
    }

    @Override
    public void elementRender(ModifiedGraphics mg) {

        mg.textFamily(family);
        mg.textPosture(posture);
        mg.textSize(size);

        mg.fillNoStroke(color);
        mg.text(text,position.x,position.y,0,mg.textAscent());

    }

    @Override
    public Bounds getBounds() {
        return bounds;
    }

    @Override
    public void calculateBounds(ModifiedGraphics mg) {
        mg.textFamily(family);
        mg.textPosture(posture);
        mg.textSize(size);
        bounds = new Bounds(
                position,
                new Dimension(
                        mg.textWidth(text),
                        mg.textHeight()
                )
        );
    }

}

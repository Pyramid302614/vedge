package org.py.vedge.ii.elements;

import org.py.vedge.graphics.external.Font;
import org.py.vedge.graphics.external.ModifiedGraphics;
import org.py.vedge.ii.Bounds;
import org.py.vedge.ii.Dimension;

public class Label extends Element {

    public String text;

    public void text(String text) {
        this.text = text;
    }

    private Bounds bounds;

    public Font font = Font.ARIAL;

    public Label(String text) {
        this.text = text;
    }

    public Label font(Font font) {
        this.font = font;
        return this;
    }

    @Override
    public void elementRender(ModifiedGraphics mg) {

        mg.setFont(font);

        mg.text(text,position.x,position.y,0,mg.textAscent());

    }

    @Override
    public Bounds getBounds() {
        return bounds;
    }

    @Override
    public void calculateBounds(ModifiedGraphics mg) {
        mg.setFont(font);
        bounds = new Bounds(
                position,
                new Dimension(
                        mg.textWidth(text),
                        mg.textHeight()
                )
        );
    }

}

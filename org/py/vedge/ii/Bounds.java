package org.py.vedge.ii;

import org.py.vedge.graphics.Color;
import org.py.vedge.graphics.Graphics;
import org.py.vedge.graphics.ModifiedGraphics;

public record Bounds(Position position, Dimension dimension) {

    public void render(Graphics g) {
        g.strokeNoFill(Color.GREEN);
        g.rect(position.x,position.y,dimension.w,dimension.h);
    }
    public void render(ModifiedGraphics mg) {
        mg.strokeNoFill(Color.GREEN);
        mg.rect(position.x,position.y,dimension.w,dimension.h);
    }

}

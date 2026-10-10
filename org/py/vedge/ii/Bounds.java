package org.py.vedge.ii;

import org.py.vedge.graphics.Graphics;
import org.py.vedge.graphics.ModifiedGraphics;
import org.py.vedge.infa.Debug;

public record Bounds(Position position, Dimension dimension) {

    public void render(Graphics g) {
        g.strokeNoFill(Debug.RENDER_ELEMENT_BOUNDS_Color);
        g.rect(position.x,position.y,dimension.w,dimension.h);
    }
    public void render(ModifiedGraphics mg) {
        mg.strokeNoFill(Debug.RENDER_ELEMENT_BOUNDS_Color);
        mg.rect(position.x,position.y,dimension.w,dimension.h);
    }

    @Override
    public String toString() {
        return "Bounds : x" + position.x + " y" + position.y + " w" + dimension.w + " h" + dimension.h;
    }

}

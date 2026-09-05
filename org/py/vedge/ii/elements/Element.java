package org.py.vedge.ii.elements;

import org.py.vedge.graphics.Graphics;
import org.py.vedge.graphics.ModifiedGraphics;
import org.py.vedge.ii.Position;
import org.py.vedge.ii.Token;

public class Element extends Token {

    public final Styles styles = new Styles();

    public Element() {
        styles.set("transparency",new Property(100));
    }

    public void onEnter() {}
    public void onExit() {}

    public Element position(double x, double y) {
        position = new Position(x,y);
        return this;
    }

    public void elementTick() {}
    @Override
    public final void tokenTick() {
        styles.tick();
        elementTick();
    }

    public final void elementRender(Graphics g) {
        elementRender(
                new ModifiedGraphics(
                        g,
                        position.x,
                        position.y,
                        1,
                        1
                ).setTransparency(styles.get("transparency").asNumber())
        );
    }
    public void elementRender(ModifiedGraphics g) {}

}
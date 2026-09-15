package org.py.vedge.ii.elements;

import org.py.vedge.graphics.Graphics;
import org.py.vedge.graphics.ModifiedGraphics;
import org.py.vedge.ii.Bounds;
import org.py.vedge.ii.Position;
import org.py.vedge.ii.Token;
import org.py.vedge.infa.Window;

public class Element extends Token {

    public Element parent = null; // null = no parents, orphan

    public final Styles styles = new Styles();

    // set()
    public Element Style(String key, Property value) {
        styles.set(key,value);
        return this;
    }
    // setValue()
    public Element style(String key, Property value) {
        styles.setValue(key,value);
        return this;
    }

    public Element() {
        styles.set("transparency",new Property(100));
        styles.set("alignment",new Property(Property.P_Alignment.None));
    }

    public void onEnter() {}
    public void onExit() {}

    public Element position(double x, double y) {
        position = new Position(x,y);
        return this;
    }
    public Element x(double x) {
        position.x = x;
        return this;
    }
    public Element y(double y) {
        position.y = y;
        return this;
    }

    public void elementTick() {}
    @Override
    public final void tokenTick() {
        styles.tick();
        elementTick();
    }

    @Override
    public final void tokenRender(Graphics g) {
        double xOff = 0;
        double yOff = 0;
        Property.P_Alignment alignment = styles.get("alignment").asAlignment();
        Bounds bounds = getBounds();
        if(bounds != null && alignment != Property.P_Alignment.None) {
            Bounds parentBounds = ((parent == null || parent.getBounds() == null) ? Window.getBounds() : parent.getBounds());
            yOff = switch(alignment) {
                case LeftCenter, RightCenter, TopCenter, BottomCenter, Center -> {
                    xOff = parentBounds.position().x + ( parentBounds.dimension().w - bounds.dimension().w ) / 2;
                    yield parentBounds.position().y + ( parentBounds.dimension().h - bounds.dimension().h ) / 2;
                }
                default -> yOff;
            };
            switch(alignment) {
                case Top:
                case TopCenter:
                    yOff = parentBounds.position().y;
                    break;
                case Left:
                case LeftCenter:
                    xOff = parentBounds.position().x;
                    break;
                case Right:
                case RightCenter:
                    xOff = parentBounds.position().x + (parentBounds.dimension().w - bounds.dimension().w);
                    break;
                case Bottom:
                case BottomCenter:
                    yOff = parentBounds.position().y + (parentBounds.dimension().h - bounds.dimension().h);
                    break;
            }

        }
        elementRender(
                new ModifiedGraphics(
                        g,
                        position.x + xOff,
                        position.y + yOff,
                        1,
                        1
                ).setTransparency(styles.get("transparency").asNumber())
        );
    }
    public void elementRender(ModifiedGraphics g) {}

    public Bounds getBounds() {
        return null;
    }

}
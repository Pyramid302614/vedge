package org.py.vedge.ii.elements;

import org.py.vedge.graphics.Color;
import org.py.vedge.graphics.Graphics;
import org.py.vedge.graphics.ModifiedGraphics;
import org.py.vedge.ii.Bounds;
import org.py.vedge.ii.Position;
import org.py.vedge.ii.Token;
import org.py.vedge.infa.Debug;
import org.py.vedge.infa.Window;

public class Element extends Token {

    public Element parent = null; // null = no parents, orphan

    public final Styles styles = new Styles();

    // set() - overrides *EVERY* value of the Property object with the argument
    public Element style(String key, Property value) {
        styles.set(key,value);
        return this;
    }
    // setValue() - overrides VALUE of the Property object with the argument's VALUE, DOESN'T CARRY OVER EASE AND THINGS
    public Element style(String key, Color value) {
        styles.setValue(key,value);
        return this;
    }
    public Element style(String key, double value) {
        styles.setValue(key,value);
        return this;
    }
    public Element style(String key, Property.P_Alignment value) {
        styles.setValue(key,value);
        return this;
    }

    public Element() {
        styles.set("transparency",new Property(100));
        styles.set("alignment",new Property(Property.P_Alignment.None));
        styles.set("x_offset",new Property(0));
        styles.set("y_offset",new Property(0));
        styles.set("w_offset",new Property(0));
        styles.set("h_offset",new Property(0));
        styles.set("scale_x",new Property(1));
        styles.set("scale_y",new Property(1));
        styles.set("scale_center",new Property(Property.P_Alignment.Center));
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
        final double scaleX = styles.get("scale_x").asNumber();
        final double scaleY = styles.get("scale_y").asNumber();
        double xOff = 0;
        double yOff = 0;
        Property.P_Alignment alignment = styles.get("alignment").asAlignment();
        calculateBounds(new ModifiedGraphics(g,0,0,scaleX,scaleY));
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
        xOff += styles.get("x_offset").asNumber() - 1/styles.get("scale_x").asNumber();
        yOff += styles.get("y_offset").asNumber() - 1/styles.get("scale_y").asNumber();
        ModifiedGraphics mg = new ModifiedGraphics(
                g,
                position.x + xOff,
                position.y + yOff,
                scaleX,
                scaleY
        ).setTransparency(styles.get("transparency").asNumber());
        elementRender(mg);
        if(Debug.RENDER_ELEMENT_BOUNDS) {
            Bounds b = getBounds();
            if(b != null) {
                b.render(mg.setTransparency(100));
            }
        }
    }
    public void elementRender(ModifiedGraphics g) {}

    public Bounds getBounds() {
        return null;
    }
    public void calculateBounds(ModifiedGraphics mg) {} // mg - position will always be 0,0

}
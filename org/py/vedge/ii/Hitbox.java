package org.py.vedge.ii;

import org.py.vedge.util.Sparry;

public class Hitbox extends Polygon {

    private final Token owner;

    public Hitbox(Token owner) {
        this.owner = owner;
    }
    public Hitbox(Token owner, Polygon shape) {
        this(owner);
        setShape(shape);
    }

    public Sparry<DoublePoint> getRelativePoints() {
        return this;
    }
    public Sparry<DoublePoint> getCanvasPoints() {
        Sparry<DoublePoint> result = new Sparry<>();
        this.forEach(i -> result.add(new DoublePoint(owner.TCX(i.x()),owner.TCY(i.y()))));
        return result;
    }
    public void setShape(Polygon polygon) {
        if(length > 0) clear();
        addAll(polygon);
    }

}

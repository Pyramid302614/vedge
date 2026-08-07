package org.py.vedge;

public class Hitbox extends Polygon2D {

    private final Token owner;

    public Hitbox(Token owner) {
        this.owner = owner;
    }

    public Sparry<DoublePoint> getRelativePoints() {
        return this;
    }
    public Sparry<DoublePoint> getCanvasPoints() {
        Sparry<DoublePoint> result = new Sparry<>();
        this.forEach(i -> result.add(new DoublePoint(owner.TCX(i.x()),owner.TCY(i.y()))));
        return result;
    }
    public void setShape(Polygon2D polygon) {
        if(length > 0) clear();
        addAll(polygon);
    }

}

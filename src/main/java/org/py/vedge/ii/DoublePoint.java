package org.py.vedge.ii;

public record DoublePoint(double x, double y) {

    public Position toPosition() {
        return new Position(x,y);
    }
    public DoublePoint toDoublePoint() {
        return new DoublePoint(x,y);
    }

}

package org.py.vedge.ii;

import java.util.Objects;

public final class DoublePoint {

    private double x;
    private double y;

    public DoublePoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Position toPosition() {
        return new Position(x, y);
    }

    public DoublePoint toDoublePoint() {
        return new DoublePoint(x, y);
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) return true;
        if(obj == null || obj.getClass() != this.getClass()) return false;
        var that = (DoublePoint) obj;
        return Double.doubleToLongBits(this.x) == Double.doubleToLongBits(that.x) &&
                Double.doubleToLongBits(this.y) == Double.doubleToLongBits(that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "DoublePoint[" +
                "x=" + x + ", " +
                "y=" + y + ']';
    }


    public static final DoublePoint ZERO = new DoublePoint(0,0);


}

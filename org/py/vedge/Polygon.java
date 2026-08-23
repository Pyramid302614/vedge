package org.py.vedge;

public class Polygon extends Sparry<DoublePoint> {

    public Polygon(DoublePoint... points) {
        addAll(points);
    }

    public static Polygon rect(double x, double y, double w, double h) {
        return new Polygon(
                new DoublePoint(x,y),
                new DoublePoint(x,y+h),
                new DoublePoint(x+w,y+h),
                new DoublePoint(x+w,y)
        );
    }

}
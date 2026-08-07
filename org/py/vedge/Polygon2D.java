package org.py.vedge;

public class Polygon2D extends Sparry<DoublePoint> {

    public Polygon2D(DoublePoint... points) {
        addAll(points);
    }

    public static Polygon2D rect(double x, double y, double w, double h) {
        return new Polygon2D(
                new DoublePoint(x,y),
                new DoublePoint(x,y+h),
                new DoublePoint(x+w,y+h),
                new DoublePoint(x+w,y)
        );
    }

}
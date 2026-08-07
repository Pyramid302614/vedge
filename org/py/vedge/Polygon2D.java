package org.py.vedge;

public class Polygon2D extends Sparry<DoublePoint2D> {

    public Polygon2D(DoublePoint2D... points) {
        addAll(points);
    }

    public static Polygon2D rect(double x, double y, double w, double h) {
        return new Polygon2D(
                new DoublePoint2D(x,y),
                new DoublePoint2D(x,y+h),
                new DoublePoint2D(x+w,y+h),
                new DoublePoint2D(x+w,y)
        );
    }

}
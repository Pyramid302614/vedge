package org.py.vedge.ii;

public class Meth {

    // Around (0,0)
    public static DoublePoint rotate(DoublePoint point, double degrees) {
        return rotate(point.x(),point.y(),degrees);
    }
    public static DoublePoint rotate(double x, double y, double degrees) {
        if(degrees == 0) return new DoublePoint(x,y);
        double hypotenuse = Math.hypot(x,y);
        double angle = Math.atan2(x,y);
        return new DoublePoint(
                Math.sin(angle-Math.toRadians(degrees))*hypotenuse,
                Math.cos(angle-Math.toRadians(degrees))*hypotenuse
        );
    }
    public static DoublePoint rotate(DoublePoint point, double degrees, DoublePoint origin) {
        return rotate(point.x(),point.y(),degrees,origin);
    }
    public static DoublePoint rotate(double x, double y,double degrees,DoublePoint origin) {
        DoublePoint p = rotate(x-origin.x(),y-origin.y(),degrees);
        return new DoublePoint(p.x()+origin.x(),p.y()+origin.y());
    }


}

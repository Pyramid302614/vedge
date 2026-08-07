package org.py.vedge;

public class Meth {

    // Around (0,0)
    public DoublePoint rotate(DoublePoint point,double radians) {
        return rotate(point.x(),point.y(),radians);
    }
    public DoublePoint rotate(double x, double y, double radians) {
        double hypotenuse = Math.hypot(x,y);
        return new DoublePoint(
                Math.sin(radians)*hypotenuse,
                Math.cos(radians)*hypotenuse
        );
    }
    public DoublePoint rotate(DoublePoint point,double radians,DoublePoint origin) {
        DoublePoint p = rotate(point.x()-origin.x(),point.y()-origin.y(),radians);
        return new DoublePoint(p.x()+origin.x(),p.y()+origin.y());
    }


}

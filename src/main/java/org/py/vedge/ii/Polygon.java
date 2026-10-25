package org.py.vedge.ii;

import org.py.vedge.util.Sparry;

public class Polygon extends Sparry<DoublePoint> {

    public boolean isElliptic = false;
    public double isElliptic__x;
    public double isElliptic__y;
    public double isElliptic__w;
    public double isElliptic__h;

    public Polygon(DoublePoint... points) {
        addAll(points);
    }

    // Returns new object
    public Polygon scale(DoublePoint scale) {
        return scale(scale.x(),scale.y());
    }
    public Polygon scale(double xy) {
        return scale(xy,xy);
    }
    public Polygon scale(double x, double y) {
        if(isElliptic) {
            return Polygon.ellipse(isElliptic__x*=x,isElliptic__y*y,isElliptic__w*x,isElliptic__h*y);
        }
        Polygon poly = new Polygon();
        poly.addAll(this);
        poly.map(i -> new DoublePoint(i.x()*x,i.y()*y));
        return poly;
    }
    // Returns new object
    public Polygon offset(DoublePoint offset) {
        return offset(offset.x(),offset.y());
    }
    public Polygon offset(double x, double y) {
        if(isElliptic) {
            return Polygon.ellipse(isElliptic__x+x,isElliptic__y+y,isElliptic__w+x,isElliptic__h+y);
        }
        Polygon poly = new Polygon();
        poly.addAll(this);
        poly.map(i -> new DoublePoint(i.x()+x,i.y()+y));
        return poly;
    }

    public DoublePoint center() {
        double left = get(0).x();
        for(int i = 1; i < length; i++) {
            if(get(i).x() < left) left = get(i).x();
        }
        double right = get(0).x();
        for(int i = 1; i < length; i++) {
            if(get(i).x() > right) right = get(i).x();
        }
        double up = get(0).y();
        for(int i = 1; i < length; i++) {
            if(get(i).y() < up) up = get(i).y();
        }
        double down = get(0).y();
        for(int i = 1; i < length; i++) {
            if(get(i).y() > down) down = get(i).y();
        }
        return new DoublePoint(
            (left+right)/2.0,
            (up+down)/2.0
        );
    }

    public static Polygon rect(double x, double y, double w, double h) {
        return new Polygon(
                new DoublePoint(x,y),
                new DoublePoint(x,y+h),
                new DoublePoint(x+w,y+h),
                new DoublePoint(x+w,y)
        );
    }
    public static Polygon ellipse(double x, double y, double w, double h) {
        Polygon ellipse = new Polygon();
        ellipse.isElliptic = true;
        ellipse.isElliptic__x = x;
        ellipse.isElliptic__y = y;
        ellipse.isElliptic__w = w;
        ellipse.isElliptic__h = h;
        return ellipse;
    }

}

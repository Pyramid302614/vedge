package org.py.vedge.ii;

public class Dimension {

    public double w = 0;
    public double h = 0;

    public Dimension() {}
    public Dimension(double w, double h) {
        this.w = w;
        this.h = h;
    }

    public void set(double w, double h) {
        this.w = w;
        this.h = h;
    }

    public Dimension scale(double x, double y) {
        return new Dimension(w*x,h*y);
    }
    public Dimension scale(DoublePoint dp) {
        return scale(dp.x(),dp.y());
    }

    @Override
    public String toString() {
        return "[w="+w+", h="+h+"]";
    }

}

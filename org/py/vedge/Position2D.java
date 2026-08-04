package org.py.vedge;

public class Position2D {

    public double x = 0;
    public double y = 0;

    public Velocity2D velocity = null;

    public Position2D() {}
    public Position2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void configureVelocity(double friction) {
        velocity = new Velocity2D(friction);
    }

}

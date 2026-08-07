package org.py.vedge;

public class Velocity2D {

    public double x = 0;
    public double y = 0;

    public double friction;

    public void tick() {

        if(Math.abs(x) > friction / 10) x -= (Math.abs(x)/x) * (friction / 10); else x = 0.0;
        if(Math.abs(y) > friction / 10) y -= (Math.abs(y)/y) * (friction / 10); else y = 0.0;

    }

    public Velocity2D(double friction) {
        this.friction = friction;
    }

    // [ X, Y ]
    public void push(double x, double y) {
        this.x += x / 10;
        this.y += y / 10;
    }

}

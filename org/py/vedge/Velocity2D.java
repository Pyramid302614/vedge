package org.py.vedge;

public class Velocity2D {

    public double x = 0;
    public double y = 0;

    public double friction;

    public void tick() {

        if(x > friction) x -= friction; else x = 0.0;
        if(y > friction) y -= friction; else y = 0.0;

    }

    public Velocity2D(double friction) {
        this.friction = friction;
    }

    // [ X, Y ]
    public void push(double[] amount) {
        x += amount[0];
        y += amount[1];
    }

}

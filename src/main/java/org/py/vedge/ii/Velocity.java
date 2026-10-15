package org.py.vedge.ii;

import org.py.vedge.Vedge;

public class Velocity {

    public double x = 0;
    public double y = 0;

    public double friction;

    public void tick() {

        if(Math.abs(x) > friction) x -= (Math.abs(x)/x) * (friction * Vedge.tickSpeed); else x = 0.0;
        if(Math.abs(y) > friction) y -= (Math.abs(y)/y) * (friction * Vedge.tickSpeed); else y = 0.0;

    }

    public Velocity(double friction) {
        this.friction = friction;
    }

    // [ X, Y ]
    public void push(double x, double y) {
        this.x += x * Vedge.tickSpeed;
        this.y += y * Vedge.tickSpeed;
    }

}

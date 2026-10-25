package org.py.vedge.ii;

import org.py.vedge.Vedge;

public class Position {

    public double x = 0;
    public double y = 0;

    public Velocity velocity = null;

    public void tick() {

        if(velocity != null) {
            x += velocity.x;
            y += velocity.y;
            velocity.tick();
        }

    }

    public Position() {}
    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void configureVelocity(double friction) {
        velocity = new Velocity(friction);
    }

    public boolean equals(Position pos2d) {

        return pos2d.x == x && pos2d.y == y;

    }
    public Position copy() {

        Position pos2d = new Position();
        pos2d.x = x;
        pos2d.y = y;
        return pos2d;

    }
    public void set(Position other) { // Essentially conform() but without the other stuff
        x = other.x;
        y = other.y;
    }
    public void set(DoublePoint other) {
        x = other.x();
        y = other.y();
    }
    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Position add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }
    public Position add(DoublePoint xy) {
        return add(xy.x(),xy.y());
    }

    // Returns copy
    public Position scale(DoublePoint scale) {
        return new Position(x*scale.x(),y*scale.y());
    }
    public Position rScale(DoublePoint scale) {
        return new Position(x/scale.x(),y/scale.y());
    }
    public Position offset(DoublePoint offset) {
        return new Position(x+offset.x(),y+offset.y());
    }
    public Position rOffset(DoublePoint offset) {
        return new Position(x-offset.x(),y-offset.y());
    }

    @Override
    public String toString() {
        return "Position : x" + x + " y" + y;
    }

    public DoublePoint toDPoint() {
        return new DoublePoint(x,y);
    }

}

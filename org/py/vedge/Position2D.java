package org.py.vedge;

public class Position2D {

    public double x = 0;
    public double y = 0;

    public Velocity2D velocity = null;

    public void tick() {

        if(velocity != null) {
            x += velocity.x;
            y += velocity.y;
            velocity.tick();
        }

    }

    public Position2D() {}
    public Position2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void configureVelocity(double friction) {
        velocity = new Velocity2D(friction);
    }

    public boolean equals(Position2D pos2d) {

        return pos2d.x == x && pos2d.y == y;

    }
    public Position2D copy() {

        Position2D pos2d = new Position2D();
        pos2d.x = x;
        pos2d.y = y;
        return pos2d;

    }

}

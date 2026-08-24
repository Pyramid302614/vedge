package org.py.vedge.ii;

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

}

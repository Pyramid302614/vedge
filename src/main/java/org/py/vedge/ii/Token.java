package org.py.vedge.ii;

import org.py.vedge.data.JSONObject;
import org.py.vedge.graphics.external.Graphics;
import org.py.vedge.physics.external.PhysicsObject;
import org.py.vedge.util.Sparry;

import java.awt.*;
import java.util.function.Consumer;

public class Token {

    public Position position = new Position(0,0);
    public Position previousPosition = new Position(0,0);
    public double rotation = 0;
    private final Sparry<Consumer<Position>> onPositionUpdates = new Sparry<>();
    public void onPositionUpdate(Consumer<Position> consumer) {
        onPositionUpdates.add(consumer);
    }
    public void offPositionUpdate(Consumer<Position> consumer) {
        onPositionUpdates.remove(consumer);
    }

    public Dimension size = new Dimension(1,1);

    public Hitbox hitbox = new Hitbox(this);
    public Hitbox physicsHitbox = new Hitbox(this);
    public PhysicsObject physics; // Short name to make it easier to do stuff with it
    public JSONObject physicsConfig; // Always directly inserted into PhysicsObject objects created from this token (it syncs)
    public boolean collidable = true;
    public Token hitbox(Polygon polygon) {
        hitbox.setShape(polygon);
        return this;
    }
    public Token physicsHitbox(Polygon polygon) {
        physicsHitbox.setShape(polygon);
        return this;
    }
    public Token physicsConfig(JSONObject object) {
        physicsConfig = object;
        return this;
    }
    public Token collidable(boolean collidable) {
        this.collidable = collidable;
        return this;
    }

    public double TCX(double x) {
        return position.x + x;
    }
    public double TCY(double y) {
        return position.y + y;
    }
    public double TRX(double x) {
        return x - position.x;
    }
    public double TRY(double y) {
        return y - position.y;
    }

    // Used for when you are updating the position from the game code
    public void updatePosition(double x, double y) {
        position.x = x;
        position.y = y;
        onPositionUpdates.forEach(i -> i.accept(position));
    }

    public void tokenRender(Graphics g) {}
    public final void render(Graphics g) {

        tokenRender(g);

    }

    public void onCollide(Token[] colliders, Point pointOfCollision) {}


    public final void tick() {

        previousPosition = position.copy();
        position.tick();
        tokenTick();

    }
    public void tokenTick() {}

}

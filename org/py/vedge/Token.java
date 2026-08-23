package org.py.vedge;

import java.awt.*;

public class Token {

    public Position position = new Position(0,0);
    public Position previousPosition = new Position(0,0);

    public Hitbox hitbox = new Hitbox(this);

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

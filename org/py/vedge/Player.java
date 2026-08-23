package org.py.vedge;

public class Player extends Token {

    public static Player me;

    public Player() {}
    public Player(Polygon hitbox) {
        this.hitbox = new Hitbox(this,hitbox);
    }

    public void playerTick() {

    }
    @Override
    public final void tokenTick() {
        playerTick();
    }

}

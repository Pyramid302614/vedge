package org.py.vedge.ii;

import org.py.vedge.graphics.external.Graphics;
import org.py.vedge.graphics.external.ModifiedGraphics;

public class Player extends Token {

    public static Player me;

    public Player() {}
    public Player(Polygon hitbox) {
        this.hitbox = new Hitbox(this,hitbox);
    }

    public void playerRender(ModifiedGraphics mg) {

    }
    @Override
    public final void tokenRender(Graphics g) {
        playerRender(new ModifiedGraphics(g,position.x,position.y,size.w,size.h));
    }

    public void playerTick() {

    }
    @Override
    public final void tokenTick() {
        playerTick();
    }

}

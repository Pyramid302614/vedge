package org.py.vedge.ii;

import org.py.vedge.graphics.ModifiedGraphics;

public class LevelTile extends Token {

    public final int key;

    public LevelTile(int key) {
        this.key = key;
    }
    public LevelTile(int key, Polygon hitbox) {
        this(key);
        this.hitbox = new Hitbox(this,hitbox);
    }

    public void tileRender(ModifiedGraphics mg) {}

}
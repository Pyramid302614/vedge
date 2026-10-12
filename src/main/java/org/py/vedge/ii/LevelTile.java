package org.py.vedge.ii;

import org.py.vedge.graphics.Color;
import org.py.vedge.graphics.external.ModifiedGraphics;

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

    public static LevelTile SOLID_SQUARE(int key, Color color) {
        return new LevelTile(key,Polygon.rect(0,0,1,1)) {
            @Override
            public void tileRender(ModifiedGraphics mg) {
                mg.fill(color);
                mg.rect(0,0,1,1);
            }
        };
    }

}
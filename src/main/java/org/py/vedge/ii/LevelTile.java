package org.py.vedge.ii;

import org.py.vedge.graphics.Color;
import org.py.vedge.graphics.external.ModifiedGraphics;

import java.awt.*;
import java.util.function.Consumer;

public class LevelTile extends Token {

    public final int key;
    public Consumer<ModifiedGraphics> render = (mg) -> {};

    public LevelTile(int key) {
        this.key = key;
    }
    public LevelTile(int key, Polygon hitbox, Consumer<ModifiedGraphics> render) {
        this(key);
        this.hitbox = new Hitbox(this,hitbox);
        this.render = render;
    }

    public LevelTile raster(LevelMap map, Point location) { // Location: point on level map where this tile is found
        LevelTile tile = new LevelTile(
            key,
            hitbox.scale(map.tileSize),
            render
        );
        tile.physicsHitbox(physicsHitbox.scale(map.tileSize));
        tile.position.set(location.x * map.tileSize, location.y * map.tileSize);
        tile.size.set(map.tileSize,map.tileSize);
        return tile;
    }

    public void tileRender(ModifiedGraphics mg) {
        render.accept(mg);
    }

    public static LevelTile SOLID_SQUARE(int key, Color fill, Color stroke) {
        return new LevelTile(key,Polygon.rect(0,0,1,1),mg -> {
                if(fill == null || fill.blank()) mg.noFill(); else mg.fill(fill);
                if(stroke == null || stroke.blank()) mg.noStroke(); else mg.stroke(stroke);
                mg.rect(0,0,1,1);
        });
    }
    public static LevelTile AIR(int key) {
        return (LevelTile)new LevelTile(key).collidable(false);
    }

}

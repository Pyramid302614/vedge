package org.py.vedge;
public class LevelTileSet {

    private final Sparry<LevelTile2D> v;

    public LevelTileSet(Sparry<LevelTile2D> v) {
        this.v = v;
    }
    public LevelTileSet(LevelTile2D... v) {
        this.v = new Sparry<>(v);
    }

    public LevelTile2D get(int key) {
        for(LevelTile2D tile : v) {
            if(tile.key == key) return tile;
        }
        return null;
    }

    public Sparry<LevelTile2D> v() {
        return v;
    }

}
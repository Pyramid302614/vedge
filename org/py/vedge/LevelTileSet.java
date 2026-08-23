package org.py.vedge;
public class LevelTileSet {

    private final Sparry<LevelTile> v;

    public LevelTileSet(Sparry<LevelTile> v) {
        this.v = v;
    }
    public LevelTileSet(LevelTile... v) {
        this.v = new Sparry<>(v);
    }

    public LevelTile get(int key) {
        for(LevelTile tile : v) {
            if(tile.key == key) return tile;
        }
        return null;
    }

    public Sparry<LevelTile> v() {
        return v;
    }

}
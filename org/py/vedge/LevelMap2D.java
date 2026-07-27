package org.py.vedge;

import java.util.HashMap;

public class LevelMap2D extends Map2D<LevelTile2D> {

    public static Sparry<LevelMap2D> ticked = new Sparry<>();
    public static HashMap<String,LevelMap2D> cached = new HashMap<>();
    public static HashMap<String,String> paths = new HashMap<>();
    public static HashMap<String,LevelTileSet> tileSets = new HashMap<>();

    public void load() {
        cached.put(name,this);
        ticked.add(this);
    }
    public static void load(String name) {
        if(cached.containsKey(name)) {
            ticked.add(cached.get(name));
            return;
        }
        if(!paths.containsKey(name)) {
            ErrorHandler.silent("An attempt was made to load level map \"" + name + "\", but no LevelMap2D::addLevelMap call was made with that name!");
            return;
        }
        LevelMap2D map = LevelMap2D.fromFile(paths.get(name),tileSets.get(name));
        cached.put(name,map);
        ticked.add(map);
    }
    public static void unloadAll() {
        ticked.clear();
    }
    public static void clearCache() {
        cached.clear();
    }

    public static void tickAllLevelMap2Ds() {
        ticked.forEach(LevelMap2D::tick);
    }

    public void tick() {
        forEach(LevelTile2D::runTick);
    }




    public final String name;
    public final LevelTileSet tileSet;

    public LevelMap2D(String name, LevelTileSet tileSet) {
        this.name = name;
        this.tileSet = tileSet;
    }





    public static void addLevelMap(String name, String path, LevelTileSet tileSet) {
        paths.put(name,path);
        tileSets.put(name,tileSet);
    }


    public static LevelMap2D fromFile(String path, LevelTileSet tileSet) {

        return parse(Resources.getRawContents_safe(path),tileSet);

    }
    public void toFile(String path) {

        Resources.overrideContents_safe(path,toRaw().toUTF8());

    }
    public static LevelMap2D parse(BitCluster raw, LevelTileSet tileSet) {

        if(raw == null) return null;
        raw.setByteSize(8);

        int nameLength = raw.getRangeAsInt(0,16); // in bytes
        StringBuilder name = new StringBuilder();
        for(int i = 0; i < nameLength; i++) name.append((char)(raw.getByte(2 + i)));
        int keySize = (int)Math.ceil(Math.log(tileSet.v().length)/Math.log(2)); /*while(Math.pow(2,keySize) < tileSet.v().length) keySize++;*/

        LevelMap2D output = new LevelMap2D(name.toString(),tileSet);

        int y = 0;
        int x = 0;
        for(int i = 0; i < (raw.getRawContents().length-16-nameLength*8)/keySize; i++) {

            int index = 16 + nameLength*8 + i * keySize;
            Bit[] keyBuffer = new Bit[keySize];

            for(int j = 0; j < keySize; j++) {
                keyBuffer[j] = raw.getBit(index+j);
            }

            int key = new BitCluster(keyBuffer).toInteger();

            if(key == Math.pow(2,keySize)-1) {
                y++;
                x = 0;
            } else {
                output.set(x,y,tileSet.v().get(key)); // key = index in tile set
                x++;
            }

        }

        return output;


    }
    public BitCluster toRaw() {

        if(name.length() > 255) return null;

        BitCluster bits = new BitCluster();

        bits.append(16,BitCluster.fromInteger(name.length()).getRawContents());
        bits.append(BitCluster.fromUTF8(name).getRawContents());

        int keySize = (int)Math.ceil(Math.log(tileSet.v().length)/Math.log(2));

        for(int y = 0; y < map.length; y++) {
            for(int x = 0; x < map.get(y).length; x++) {
                bits.append(keySize,BitCluster.fromInteger(get(x,y).key).getRawContents());
            }
            if(y != map.length-1) bits.append(BitCluster.fromInteger(keySize-1).getRawContents());
        }

        return bits;

    }

}

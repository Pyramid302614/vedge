package org.py.vedge;

import java.util.HashMap;

public class LevelMap extends Map2D<LevelTile> {

//    public static LevelMap2D current;
//    public static HashMap<String,LevelMap2D> cached = new HashMap<>();
//    public static HashMap<String,String> paths = new HashMap<>();
//    public static HashMap<String,LevelTileSet> tileSets = new HashMap<>();
//
//    public void load() {
//        cached.put(name,this);
//        current = this;
//    }
//    public static void load(String name) {
//        if(cached.containsKey(name)) {
//            current = cached.get(name);
//            return;
//        }
//        if(!paths.containsKey(name)) {
//            ErrorHandler.silent("An attempt was made to load level map \"" + name + "\", but no LevelMap2D::addLevelMap call was made with that name!");
//            return;
//        }
//        LevelMap2D map = LevelMap2D.fromFile(paths.get(name),tileSets.get(name));
//        cached.put(name,map);
//        current = map;
//    }
//    public static void unloadCurrent() {
//        current = null;
//    }
//    public static void clearCache() {
//        cached.clear();
//    }

    public void tick() {
        forEach(i -> {
            if(i != null) i.tick();
        });
    }




    public final String name;
    public final LevelTileSet tileSet;

    public LevelMap(String name, LevelTileSet tileSet) {
        this.name = name;
        this.tileSet = tileSet;
    }


    public Sparry<Token> asTokens() {
        return new Sparry<>();
    }



    public static double tileSize = Settings.get("vedge.render.level_map_tile_size").asDouble();

    public void render(Graphics g) {

        forEach((tile,point) -> {
            if(tile != null) tile.tileRender(new ModifiedGraphics(
                    g,
                    point.x * tileSize,
                    point.y * tileSize,
                    tileSize,
                    tileSize
            ));
        });

    }



//    public static void addLevelMap(String name, String path, LevelTileSet tileSet) {
//        paths.put(name,path);
//        tileSets.put(name,tileSet);
//    }


    public static LevelMap fromFile(String path, LevelTileSet tileSet) {

        return parse(Resources.getRawContents_safe(path),tileSet);

    }
    public void toFile(String path) {

        Resources.overrideContents_safe(path,toRaw().toUTF8());

    }
    public static LevelMap parse(BitCluster raw, LevelTileSet tileSet) {

        if(raw == null) return null;
        raw.setByteSize(8);

        int nameLength = raw.getRangeAsInt(0,16); // in bytes
        StringBuilder name = new StringBuilder();
        for(int i = 0; i < nameLength; i++) name.append((char)(raw.getByte(2 + i)));
        int keySize = Math.max((int)Math.ceil(Math.log(tileSet.v().length)/Math.log(2)),2); /*while(Math.pow(2,keySize) < tileSet.v().length) keySize++;*/

        LevelMap output = new LevelMap(name.toString(),tileSet);

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
//                System.out.println(tileSet.v().get(key));
                output.set(x,y,tileSet.get(key)); // key = index in tile set
                x++;
            }

        }

        return output;


    }
    public BitCluster toRaw() {

        if(name.length() > 255) return null;

        BitCluster bits = new BitCluster();

        bits.append(16,BitCluster.fromInteger(name.length(),16).getRawContents());
        bits.append(BitCluster.fromUTF8(name).getRawContents());

        int keySize = Math.max((int)Math.ceil(Math.log(tileSet.v().length)/Math.log(2)),2);

        for(int y = 0; y < map.length; y++) {
            for(int x = 0; x < map.get(y).length; x++) {
                bits.append(keySize,BitCluster.fromInteger(get(x,y).key,keySize).getRawContents());
            }
            if(y != map.length-1) bits.append(BitCluster.fromInteger(keySize-1,keySize).getRawContents());
        }

        return bits;

    }

    public static LevelMap fromStringFormat(String name, String[] array, HashMap<Character,Integer> lookup) {

        Sparry<LevelTile> s = new Sparry<>();
        lookup.forEach((k,v) -> s.add(null));

        LevelMap map = new LevelMap(name,new LevelTileSet(s));
        for(int y = 0; y < array.length; y++) {
            for(int x = 0; x < array[y].length(); x++) {
                char c = array[y].charAt(x);
                map.set(x,y,new LevelTile(lookup.get(c)));
            }
        }
        return map;

    }

}

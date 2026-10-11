package org.py.vedge.ii;

import org.py.vedge.data.Bit;
import org.py.vedge.data.BitCluster;
import org.py.vedge.data.JSON;
import org.py.vedge.data.JSONObject;
import org.py.vedge.graphics.external.Graphics;
import org.py.vedge.graphics.external.ModifiedGraphics;
import org.py.vedge.infa.java.Resources;
import org.py.vedge.util.Sparry;

import java.util.HashMap;

public class LevelMap extends Map<LevelTile> {

    public void tick() {
        forEach(i -> {
            if(i != null) i.tick();
        });
    }

    public final String name;
    public final LevelTileSet tileSet;
    public JSONObject metadata;

    public LevelMap(String name, LevelTileSet tileSet) {
        this(name,tileSet,new JSONObject());
    }
    public LevelMap(String name, LevelTileSet tileSet, JSONObject metadata) {
        this.name = name;
        this.tileSet = tileSet;
        this.metadata = metadata;
    }


    public Sparry<Token> asTokens() {
        return new Sparry<>();
    }



    public double tileSize = 100;

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


    public static LevelMap fromFile(String path, LevelTileSet tileSet) {

        return parse(Resources.getRawContents_safe(path),tileSet);

    }
    public void toFile(String path) {

        Resources.overrideRawContents_safe(path,toRaw());

    }
    public static LevelMap parse(BitCluster raw, LevelTileSet tileSet) {

        if(raw == null) return null;

        long metadataLocation = -1; // In bits

        int keySize = raw.getRangeAsInt(0,8);
        int nameLength = raw.getRangeAsInt(8,24); // in bytes
        StringBuilder name = new StringBuilder();
        for(int i = 0; i < nameLength; i++) name.append((char)(raw.getByte(2 + i)));

        LevelMap output = new LevelMap(name.toString(),tileSet);

        int y = 0;
        int x = 0;
        for(int i = 0; i < (raw.getRawContents().length-24-nameLength*8)/keySize; i++) {

            // i = bytes -- index = [bits, literal position]
            int index = 24 + nameLength*8 + i * keySize;
            Bit[] keyBuffer = new Bit[keySize];

            for(int j = 0; j < keySize; j++) {
                keyBuffer[j] = raw.getBit(index+j);
            }

            int key = new BitCluster(keyBuffer).toInteger();
            if(key == Math.pow(2,keySize)-1) { // New line
                y++;
                x = 0;
            } else if(key == Math.pow(2,keySize)-2) { // Metadata declaration
                metadataLocation = index+keySize;
                break;
            } else {
                output.set(x,y,tileSet.get(key)); // key = index in tile set
                x++;
            }

        }

        if(metadataLocation != -1) {
//            System.out.println(raw.getRangeAsString(metadataLocation,raw.length()));
            output.metadata = JSON.parse(raw.getRangeAsString(metadataLocation,raw.length()));

        }

        return output;


    }
    public BitCluster toRaw() {

        if(name.length() > 255) return null;

        BitCluster bits = new BitCluster();

        int keySize = Math.max((int)Math.ceil(Math.log(tileSet.v().length)/Math.log(2)),2)+1; // +1 = room for FF and FE

        bits.append(8,BitCluster.fromInteger(keySize,8).getRawContents());
        bits.append(16,BitCluster.fromInteger(name.length(),16).getRawContents());
        bits.append(BitCluster.fromUTF8(name).getRawContents());

        for(int y = 0; y < map.length; y++) {
            for(int x = 0; x < map.get(y).length; x++) {
                bits.append(keySize,BitCluster.fromInteger(get(x,y).key,keySize).getRawContents());
            }
            if(y != map.length-1) bits.append(BitCluster.fromInteger((int)Math.pow(2,keySize)-1,keySize).getRawContents());
        }

        if(metadata.length() > 0) {
            bits.append(BitCluster.fromInteger((int)Math.pow(2,keySize)-2,keySize).getRawContents());
            bits.append(BitCluster.fromUTF8(JSON.stringify(metadata,0)).getRawContents());
        }

        return bits;

    }

    public static LevelMap fromStringFormat(String name, String[] array, HashMap<Character,Integer> lookup, JSONObject metadata) {

        Sparry<LevelTile> s = new Sparry<>();
        lookup.forEach((k,v) -> s.add(null));

        LevelMap map = new LevelMap(name,new LevelTileSet(s));
        for(int y = 0; y < array.length; y++) {
            for(int x = 0; x < array[y].length(); x++) {
                char c = array[y].charAt(x);
                map.set(x,y,new LevelTile(lookup.get(c)));
            }
        }
        map.metadata = metadata;

        return map;

    }

}

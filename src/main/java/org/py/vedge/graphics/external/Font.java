package org.py.vedge.graphics.external;

import com.badlogic.gdx.files.FileHandle;
import org.py.vedge.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import org.py.vedge.infa.external.Pedro;
import org.py.vedge.infa.java.Resources;
import org.py.vedge.util.Vector2;

import java.util.HashMap;

public class Font {

    public final HashMap<Vector2<String,Integer>,BitmapFont> cachedRasters = new HashMap<>();

    public final String path;
    private int size = 12;
    private Color color = Color.BLACK;

    private BitmapFont raster;

    public static Font ARIAL = new Font(";/ARIAL.TTF");

    public static Font ARIAL() {
        return ARIAL.clone();
    }

    public Font(String path) {
        this.path = Resources.resolve(path);
        raster();
    }
    public Font setSize(int size) {
        if(this.size != size) {
            this.size = size;
            raster();
        }
        return this;
    }
    public int getSize() {
        return size;
    }
    public Font setColor(Color color) {
        this.color = color;
        return this;
    }
    public Color getColor() {
        return color;
    }
    public Font conform(Font otherFont) {
        return setSize(otherFont.size).setColor(otherFont.color);
    }

    private void raster() {
        // Search cache
        for(Vector2<String,Integer> k : cachedRasters.keySet()) {
            if(
                k.a().equals(path) &&
                    k.b() == size
            ) {
                raster = cachedRasters.get(k);
                return;
            }
        }
        // If not found in cache
        raster = loadFont(path,size);
    }

    public BitmapFont getRaster() {
        return raster;
    }

    @Override
    public Font clone() {
        return new Font(path).setSize(size).setColor(color);
    }

    private static BitmapFont loadFont(String filePath, int size) { // Color is white for ez-pz tinting

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(new FileHandle(filePath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.genMipMaps = true;
        parameter.minFilter = Texture.TextureFilter.MipMapLinearLinear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.color = Pedro.vg_gdx(Color.WHITE);
        parameter.borderStraight = true;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;

    }

}

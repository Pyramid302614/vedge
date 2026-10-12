package org.py.vedge.graphics.external;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import org.py.vedge.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import org.py.vedge.infa.external.Pedro;
import org.py.vedge.infa.java.ErrorHandler;
import org.py.vedge.infa.java.Resources;
import org.py.vedge.util.Sparry;
import org.py.vedge.util.Vector3;

import java.util.HashMap;
import java.util.function.Supplier;

public class Font {

    public final HashMap<Vector3<String,Integer,Color>,BitmapFont> cachedRasters = new HashMap<>();

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
    public Font setColor(Color color) {
        if(Color.equals(this.color,color)) {
            this.color = color;
            raster();
        }
        return this;
    }
    public Font setAttributes(int size, Color color) {
        boolean changed = false;
        if(this.size != size) {
            this.size = size;
            changed = true;
        }
        if(Color.equals(this.color,color)) {
            this.color = color;
            changed = true;
        }
        if(changed) raster();
        return this;
    }
    public Font conform(Font otherFont) {
        return setAttributes(otherFont.size,otherFont.color);
    }

    private void raster() {
        // Search cache
        for(Vector3<String,Integer,Color> k : cachedRasters.keySet()) {
            if(
                k.a().equals(path) &&
                    k.b() == size &&
                    Color.equals(k.c(),color)
            ) {
                raster = cachedRasters.get(k);
                return;
            }
        }
        // If not found in cache
        raster = loadFont(path,size,color);
    }

    public BitmapFont getRaster() {
        return raster;
    }

    @Override
    public Font clone() {
        return new Font(path).setAttributes(size,color);
    }

    private static BitmapFont loadFont(String filePath, int size, Color color) {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(new FileHandle(filePath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.color = Pedro.vg_gdx(color);
        parameter.genMipMaps = true;
        parameter.minFilter = Texture.TextureFilter.MipMapLinearLinear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.borderStraight = true;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        return font;

    }

}

package org.py.vedge.graphics;

import org.py.vedge.infa.java.ErrorHandler;
import org.py.vedge.util.Vector2;

public class Color {

    @SuppressWarnings("unchecked")
    private final Vector2<String,int[]>[] presets = (Vector2<String, int[]>[]) new Vector2[] {
            new Vector2<>("black",new int[] {0,0,0}),
            new Vector2<>("red",new int[] {255,0,0}),
            new Vector2<>("orange",new int[] {170,85,0}),
            new Vector2<>("yellow",new int[] {85,170,0}),
            new Vector2<>("green",new int[] {0,255,0}),
            new Vector2<>("dark green",new int[] {0,170,85}),
            new Vector2<>("cyan",new int[] {0,85,170}),
            new Vector2<>("blue",new int[] {0,0,255}),
            new Vector2<>("purple",new int[] {85,0,170}),
            new Vector2<>("magenta",new int[] {170,0,85}),
            new Vector2<>("white",new int[] {255,255,255}),
            new Vector2<>("gray",new int[] {150,150,150}),
            new Vector2<>("light_gray",new int[] {200,200,200})
    };

    private int[] rgb = { 0, 0, 0 };
    private int a = 255;
    private String hex = "#000000";
    private boolean value = false; // 0 = rgb, 1 = hex

    public Color() {}
    public Color(int r, int g, int b) {
        set(r,g,b);
    }
    public Color(int r, int g, int b, int a) {
        set(r,g,b,a);
    }
    public Color(String hexOrName) {
        set(hexOrName);
    }

    public int red() {
        return rgb[0];
    }
    public int green() {
        return rgb[1];
    }
    public int blue() {
        return rgb[2];
    }
    public int alpha() {
        return a;
    } // 0-255
    public boolean blank() {
        return a == 0;
    }
    public boolean opaque() {
        return a == 1;
    }

    public void set(int r, int g, int b) {
        rgb = new int[] { r, g, b };
        value = false;
    }
    public void set(int r, int g, int b, int a) {
        this.a = a;
        rgb = new int[] { r, g, b };
    }
    public void set(String hexOrName) {
        if(hexOrName.charAt(0) == '#') {
            hex = hexOrName;
            value = true;
        } else {
            setToPreset(hexOrName.toLowerCase());
        }
    }


    private void setToPreset(String presetName) {
        for(Vector2<String, int[]> preset : presets) {
            if(preset.a().equals(presetName)) {
                rgb = preset.b();
                value = false;
            }
        }
    }

    private Vector2<String, int[]> getPreset(String presetName) {
        for(Vector2<String, int[]> preset : presets) {
            if(preset.a().equals(presetName)) {
                return preset;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        if(value) {
            return hex;
        } else {
            return "Color : r" + rgb[0] + " : g" + rgb[1] + " : b" + rgb[2] + " : a" + a;
        }
    }

    public String toCSS() {
        if(value) {
            return hex;
        } else {
            return "rgb("+rgb[0]+","+rgb[1]+","+rgb[2]+")";
        }
    }

    public static final Color BLACK = new Color("black");
    public static final Color RED = new Color("red");
    public static final Color ORANGE = new Color("orange");
    public static final Color YELLOW = new Color("yellow");
    public static final Color GREEN = new Color("green");
    public static final Color DARK_GREEN = new Color("dark green");
    public static final Color CYAN = new Color("cyan");
    public static final Color BLUE = new Color("blue");
    public static final Color PURPLE = new Color("purple");
    public static final Color MAGENTA = new Color("magenta");
    public static final Color WHITE = new Color("white");
    public static final Color GRAY = new Color("gray");
    public static final Color LIGHT_GRAY = new Color("light_gray");
    public static final Color BLANK = new Color(0,0,0,0);

    @Override
    public Color clone() {
        return new Color(rgb[0],rgb[1],rgb[2],a);
    }

    public static boolean equals(Color color1, Color color2) {
        return (
            color1.red() == color2.red() &&
            color1.green() == color2.green() &&
            color1.blue() == color2.blue() &&
            color1.alpha() == color2.alpha()
        );
    }

}

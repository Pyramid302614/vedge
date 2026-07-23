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
            new Vector2<>("white",new int[] {255,255,255})
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
            if(preset.a.equals(presetName)) {
                rgb = preset.b;
                value = false;
            }
        }
    }

    private Vector2<String, int[]> getPreset(String presetName) {
        for(Vector2<String, int[]> preset : presets) {
            if(preset.a.equals(presetName)) {
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
            return rgb[0] + "," + rgb[1] + "," + rgb[2];
        }
    }

    // Remove method if javafx.scene.paint.Color is underlined red
    public javafx.scene.paint.Color toColorFX() {
        if(!value) {
            return javafx.scene.paint.Color.rgb(rgb[0], rgb[1], rgb[2], (double)a/255);
        }
        return null; // Doesn't support HEX
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
    public static final Color BLANK = new Color(0,0,0,0);

    public static javafx.scene.paint.Color colorAwt_to_colorFx(java.awt.Color awt, javafx.scene.paint.Color fx) {
        return new javafx.scene.paint.Color(awt.getRed(),awt.getGreen(),awt.getBlue(),awt.getAlpha());
    }

}

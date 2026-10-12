package org.py.vedge.graphics;

import org.py.vedge.graphics.external.Font;

import java.util.HashMap;

public class Fonts {

    public static HashMap<String,Font> storage = new HashMap<>();

    public static void addFont(String name, Font font) {
        storage.put(name,font);
    }
    public static void addFont(String name, String path) {
        addFont(name,new Font(path));
    }

    public static Font get(String name) {
        return storage.get(name);
    }

}

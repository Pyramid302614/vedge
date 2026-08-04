package org.py.vedge;

import java.util.function.Consumer;

public class InputListener {

    public static InputListener main = new InputListener();
    public static void configureMainListener() {

        Window.primaryScene.setOnKeyPressed(ke -> main.keyPressed(new Key(ke.getCode().getCode())));
        Window.primaryScene.setOnKeyReleased(ke -> main.keyReleased(new Key(ke.getCode().getCode())));

    }

    // These get triggered before group listeners do
    public static Consumer<Key> onKeyPressed;
    public static Consumer<Key> onKeyReleased;

    // These get triggered after key listeners do
    public static Consumer<String> onGroupPressed;
    public static Consumer<String> onGroupReleased;

    public void keyPressed(Key key) {
        if(onKeyPressed != null) onKeyPressed.accept(key);
        if(onGroupPressed != null) for(String g : key.keyGroupsImIn) onGroupPressed.accept(g);
    }
    public void keyReleased(Key key) {
        if(onKeyReleased != null) onKeyReleased.accept(key);
        if(onGroupReleased != null) for(String g : key.keyGroupsImIn) onGroupReleased.accept(g);
    }

    public static void setOnKeyPressed(Consumer<Key> onKeyPressed) {
        InputListener.onKeyPressed = onKeyPressed;
    }
    public static void setOnKeyReleased(Consumer<Key> onKeyReleased) {
        InputListener.onKeyReleased = onKeyReleased;
    }
    public static void setOnGroupPressed(Consumer<String> onGroupPressed) {
        InputListener.onGroupPressed = onGroupPressed;
    }
    public static void setOnGroupReleased(Consumer<String> onGroupReleased) {
        InputListener.onGroupReleased = onGroupReleased;
    }

}

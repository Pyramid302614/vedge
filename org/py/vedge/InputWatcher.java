package org.py.vedge;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputWatcher {

    public enum Type {
        KeyboardPress,
        MouseClick
    }

    public Type typeFilter;
    public int buttonCodeFilter;
    public boolean dim0filter;

    public boolean active(HashMap<Integer,Boolean> keysDown, HashMap<Integer,Boolean> mouseButtonsDown) {
        return switch(typeFilter) {
            case KeyboardPress -> dim0filter == (keysDown.containsKey(buttonCodeFilter) && keysDown.get(buttonCodeFilter));
            case MouseClick -> dim0filter == (mouseButtonsDown.containsKey(buttonCodeFilter) && mouseButtonsDown.get(buttonCodeFilter));
        };
    }
    public boolean active(boolean v, int bc) {
        return dim0filter == v && buttonCodeFilter == bc;
    }


    private boolean valid() {
        return (
            typeFilter != null
        );
    }


    public static InputWatcher parse(String string) {

        InputWatcher watcher = new InputWatcher();

        String[] split = string.split(":");

        switch(split[0]) {

            case "k","mc":

                if(string.matches(".*bc\\d+.*")) {
                    Matcher matcher = Pattern.compile("bc(\\d+)").matcher(string);
                    if(matcher.find()) watcher.buttonCodeFilter = Integer.parseInt(matcher.group().substring(2));
                }
                if(string.matches(".*df[a-z]+.*")) {
                    Matcher matcher = Pattern.compile("df[a-z]+").matcher(string);
                    if(matcher.find()) watcher.dim0filter = matcher.group().equals("dfdown");
                }

                break;

        }

        watcher.typeFilter = switch(split[0]) {

            case "k" -> Type.KeyboardPress;
            case "mc" -> Type.MouseClick;
            default -> null;

        };

        if(watcher.valid()) ErrorHandler.silent("Cannot parse watcher: " + string);

        return watcher;

    }

}

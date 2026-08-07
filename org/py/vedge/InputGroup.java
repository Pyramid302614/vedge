package org.py.vedge;

public class InputGroup extends Sparry<InputWatcher> {

    public static InputGroup parse(String string) {
        return parse(string,() -> {});
    }
    public static InputGroup parse(String string, Runnable onActive) {

        InputGroup group = new InputGroup();
        group.onActive = onActive;
        for(String part : string.split(";")) group.add(InputWatcher.parse(part));
        return group;

    }

    public Runnable onActive;

}

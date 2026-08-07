package org.py.vedge;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

public class InputListener {

    private static HashMap<String,InputGroup> inputGroups; // Gets compiled on startup
    private static final HashMap<InputGroup,Runnable> onActives = new HashMap<>();
    private static final HashMap<InputGroup,Runnable> whileActives = new HashMap<>();

    public static void compileInputGroups() {
        inputGroups = new HashMap<>();
        Settings.getFile("vedge").get("input_groups").asJSONObject().getRawHashMap().forEach((k,v) -> {
            inputGroups.put(k,InputGroup.parse(v.asString()));
        });
    }

    public static void configure() {

        Window.scene.setOnKeyPressed(ke -> keyboardEvent(true,ke.getCode().getCode()));
        Window.scene.setOnKeyReleased(ke -> keyboardEvent(false,ke.getCode().getCode()));

    }

    private static final HashMap<Integer,Boolean> keysDown = new HashMap<>();
    private static final HashMap<Integer,Boolean> mouseButtonsDown = new HashMap<>();

    public static boolean keyDown(int keyCode) {
        return keysDown.containsKey(keyCode) && keysDown.get(keyCode);
    }
    public static boolean mouseButtonDown(int buttonCode) {
        return mouseButtonsDown.containsKey(buttonCode) && mouseButtonsDown.get(buttonCode);
    }
    public static Sparry<Integer> keysDown() {
        Sparry<Integer> result = new Sparry<>();
        keysDown.forEach((k,v) -> { if(v) result.add(k); });
        return result;
    }
    public static Sparry<Integer> mouseButtonsDown() {
        Sparry<Integer> result = new Sparry<>();
        mouseButtonsDown.forEach((k,v) -> { if(v) result.add(k); });
        return result;
    }


    public static void handleWiles() {

        whileActives.forEach((g,run) -> {
            AtomicBoolean active = new AtomicBoolean(true);
            g.forEach(w -> {
                if(!w.active(keysDown,mouseButtonsDown)) active.set(false);
            });
            if(active.get()) run.run();
        });

    }

    private static void keyboardEvent(boolean v, int bc) { // Down? , KeyCode

        if(!keysDown.containsKey(bc) || keysDown.get(bc) != v) keysDown.put(bc,v);

        BiConsumer<InputGroup,Runnable> processInputGroup = (g,run) -> {
            AtomicBoolean active = new AtomicBoolean(true);
            g.forEach(w -> {
                if(!w.active(v,bc)) active.set(false);
            });
            if(active.get()) run.run();
        };

        if(!onActives.isEmpty()) onActives.forEach(processInputGroup);
        if(!inputGroups.isEmpty()) inputGroups.forEach((name,g) -> processInputGroup.accept(g,g.onActive));

    }
    private static void mouseClickEvent(boolean v, int bc) { // Down? , ButtonCode

        if(!mouseButtonsDown.containsKey(bc) || mouseButtonsDown.get(bc) != v) mouseButtonsDown.put(bc,v);

    }
    private static void mouseScrollEvent(double v) {} // Amount
    private static void mouseMoveEvent(double[] v) {} // [ xAmount, yAmount ]


    public static void onGroup(String group, Runnable onActive) {
        if(!inputGroups.containsKey(group)) throw new RuntimeException("Unknown group: " + group);
        inputGroups.get(group).onActive = onActive;
    }
    public static void on(String groupSyntax, Runnable onActive) {
        onActives.put(InputGroup.parse(groupSyntax,onActive),onActive);
    }

    // cant use while :sob:
    public static void wileGroup(String group, Runnable whileActive) {
        if(!inputGroups.containsKey(group)) throw new RuntimeException("Unknown group: " + group);
        whileActives.put(inputGroups.get(group),whileActive);
    }
    public static void wile(String groupSyntax, Runnable whileActive) {
        whileActives.put(InputGroup.parse(groupSyntax,whileActive),whileActive);
    }


}

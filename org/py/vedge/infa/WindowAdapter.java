package org.py.vedge.infa;

import org.py.vedge.graphics.Color;
import org.py.vedge.graphics.external.Graphics;
import org.py.vedge.ii.Bounds;
import org.py.vedge.ii.Dimension;
import org.py.vedge.ii.Position;
import org.py.vedge.input.KeyEvent;
import org.py.vedge.util.Sparry;

import java.util.function.Consumer;

public abstract class WindowAdapter {

    private boolean windowInitialized = false;

    public Graphics graphics;

    public final Position position = new Position(0,0);
    public Position windowPosition = new Position(
            Settings.get("vedge.window.start_position.x").asInteger(),
            Settings.get("vedge.window.start_position.y").asInteger()
    );
    public Dimension size = new Dimension(
            Settings.get("vedge.window.start_size.w").asInteger(),
            Settings.get("vedge.window.start_size.h").asInteger()
    );

    public void setPosition(Position pos) {
        windowPosition = pos;
        i_setPosition(pos);
    }
    public void setSize(Dimension size) {
        this.size = size;
        i_setSize(size);
    }

    public boolean renderBackground = true;

    private Bounds boundsCache;
    public Bounds getBounds() {
        if(boundsCache == null) boundsCache = new Bounds(position,size);
        if(boundsCache.dimension().w != size.w || boundsCache.dimension().h != size.h) boundsCache = new Bounds(position,size);
        if(boundsCache.position().x != position.x || boundsCache.position().y != position.y) boundsCache = new Bounds(position,size);
        return boundsCache;
    }

    private final Sparry<Runnable> onInitFinishes = new Sparry<>();
    public void addOnInitFinish(Runnable runnable) {
        onInitFinishes.add(runnable);
    }

    public void go() {

        if(windowInitialized) return;
        else windowInitialized = true;

        i_setTitle(Settings.get("vedge.window.name").asString());
        i_setPosition(position);
        i_setSize(size);
        i_initDraw(this::draw);
        if(Settings.get("vedge.window.exit_on_close").asBoolean()) i_setCloseOperation(() -> System.exit(0));
        graphics = i_getGraphics();

        onInitFinishes.forEach(Runnable::run);

        i_show();

    }

    private void draw() {
        if(renderBackground) {
            graphics.fill(Color.WHITE);
            graphics.rect(0,0,size.w,size.h);
        }
        Frame.frame();
    }

    // i_wont_update_any_internal_variables

    public abstract void i_setTitle(String title);
    public abstract void i_setSize(Dimension size);
    public abstract void i_setPosition(Position position);
    public abstract void i_setCloseOperation(Runnable runnable);
    public abstract void i_show();
    public abstract void i_initDraw(Runnable runnable);
    public abstract Graphics i_getGraphics();


    public abstract void i_setOnKeyPressed(Consumer<KeyEvent> consumer);
    public void setOnKeyPressed(Consumer<KeyEvent> consumer) {
        i_setOnKeyPressed(consumer);
    }
    public abstract void i_setOnKeyReleased(Consumer<KeyEvent> consumer);
    public void setOnKeyReleased(Consumer<KeyEvent> consumer) {
        i_setOnKeyReleased(consumer);
    }



}

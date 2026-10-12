package org.py.vedge.infa.external;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Graphics;
import org.py.vedge.graphics.external.Graphics;
import org.py.vedge.ii.Dimension;
import org.py.vedge.ii.Position;
import org.py.vedge.infa.WindowAdapter;
import org.py.vedge.input.KeyEvent;

import java.util.function.Consumer;

public class Window {

    private static Runnable closeOperation = () -> {};
    private static Runnable draw = () -> {};

    private static Consumer<KeyEvent> onKeyPressed = (ke) -> {};
    private static Consumer<KeyEvent> onKeyReleased = (ke) -> {};

    private static Lwjgl3ApplicationConfiguration config;

    private static boolean micPassed = false;

    public static final WindowAdapter vg = new WindowAdapter() {

        @Override
        public void i_setTitle(String title) {
            if(micPassed) Gdx.graphics.setTitle(title);
            else config.setTitle(title);
        }

        @Override
        public void i_setSize(Dimension size) {
            if(micPassed) Gdx.graphics.setWindowedMode((int)size.w,(int)size.h);
            else config.setWindowedMode((int)size.w,(int)size.h);
        }

        @Override
        public void i_setPosition(Position position) {
            if(micPassed) ((Lwjgl3Graphics)Gdx.graphics).getWindow().setPosition((int)position.x,(int)position.y);
            else config.setWindowPosition((int)position.x,(int)position.y);
        }

        @Override
        public void i_setCloseOperation(Runnable runnable) {
            closeOperation = runnable;
        }

        @Override
        public void i_initDraw(Runnable runnable) {
            draw = runnable;
        }

        @Override
        public Graphics i_getGraphics() {
            return new Graphics();
        }

        @Override
        public void i_setOnKeyPressed(Consumer<KeyEvent> consumer) {
            onKeyPressed = consumer;
        }
        @Override
        public void i_setOnKeyReleased(Consumer<KeyEvent> consumer) {
            onKeyReleased = consumer;
        }

    };

    // passTheMic - The method call(s) where the library takes over and exits when the program finishes/closes
    public static void go(Runnable passTheMic) {
        vg.addOnInitFinish(() -> micPassed = true);
        vg.go(passTheMic);
        closeOperation.run();
    }

    // <!> CALL THIS IS IN YOUR ApplicationListener::render!!!!!!!!!!!!!!!!!!!!
    public static void onRender() {
        draw.run();
    }
    // <!> ALSO CALL THIS IN YOUR ApplicationListener::create!!!!!!!!!!!!!!!!!!!!
    public static void onCreate() {
        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int code) {
                onKeyPressed.accept(new KeyEvent(code));
                return false;
            }

            @Override
            public boolean keyUp(int code) {
                onKeyReleased.accept(new KeyEvent(code));
                return true;
            }

            @Override
            public boolean keyTyped(char c) {
                return false;
            }

            @Override
            public boolean touchDown(int i, int i1, int i2, int i3) {
                return false;
            }

            @Override
            public boolean touchUp(int i, int i1, int i2, int i3) {
                return false;
            }

            @Override
            public boolean touchCancelled(int i, int i1, int i2, int i3) {
                return false;
            }

            @Override
            public boolean touchDragged(int i, int i1, int i2) {
                return false;
            }

            @Override
            public boolean mouseMoved(int i, int i1) {
                return false;
            }

            @Override
            public boolean scrolled(float v, float v1) {
                return false;
            }

        });
        vg.afterMicPassed();
    }
    public static void onDispose() {
        if(vg.graphics != null) vg.graphics.dispose();
    }

    public static void setConfig(Lwjgl3ApplicationConfiguration currentConfig) {
        config = currentConfig;
    }

}


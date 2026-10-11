package org.py.vedge.infa.external;

import javafx.animation.AnimationTimer;
import org.py.vedge.graphics.external.Graphics;
import org.py.vedge.ii.Dimension;
import org.py.vedge.ii.Position;
import org.py.vedge.infa.WindowAdapter;
import org.py.vedge.input.KeyEvent;

import java.util.function.Consumer;

public class Window {

    public static final WindowAdapter vg = new WindowAdapter() {

        @Override
        public void i_setTitle(String title) {
            FX.stage.setTitle(title);
        }

        @Override
        public void i_setSize(Dimension size) {
            FX.root.setPrefWidth(size.w);
            FX.root.setPrefHeight(size.h);
            FX.stage.sizeToScene();
//            FX.stage.setWidth(size.w+(FX.stage.getWidth()-FX.scene.getWidth()));
//            FX.stage.setHeight(size.h+(FX.stage.getHeight()-FX.scene.getHeight()));
        }

        @Override
        public void i_setPosition(Position position) {
            FX.stage.setX(position.x);
            FX.stage.setY(position.y);
        }

        @Override
        public void i_setCloseOperation(Runnable runnable) {
            FX.stage.setOnCloseRequest(e -> runnable.run());
        }

        @Override
        public void i_show() {
            FX.stage.show();
        }

        @Override
        public void i_initDraw(Runnable runnable) {
            new AnimationTimer() {
                @Override
                public void handle(long l) {
                    runnable.run();
                }
            }.start();
        }

        @Override
        public Graphics i_getGraphics() {
            return new Graphics(FX.canvas.getGraphicsContext2D());
        }

        @Override
        public void i_setOnKeyPressed(Consumer<KeyEvent> consumer) {
            FX.scene.setOnKeyPressed(ke -> consumer.accept(Pedro.fx_vg(ke)));
        }
        @Override
        public void i_setOnKeyReleased(Consumer<KeyEvent> consumer) {
            FX.scene.setOnKeyReleased(ke -> consumer.accept(Pedro.fx_vg(ke)));
        }

    };

    public static void go() {
        new FX().launch(); // -> vg.go
    }

}


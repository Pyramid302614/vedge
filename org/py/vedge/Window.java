package org.py.vedge;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.*;

public class Window extends Application {

    private static boolean windowInitialized = false;

    public static Point position = new Point(
            Settings.get("vedge.window.start_position.x").asInteger(),
            Settings.get("vedge.window.start_position.y").asInteger()
    );
    public static Dimension size = new Dimension(
            Settings.get("vedge.window.start_size.w").asInteger(),
            Settings.get("vedge.window.start_size.h").asInteger()
    );

    @Override
    public void start(Stage primaryStage) { // TODO: on close, exit or something

        if(windowInitialized) return;
        else windowInitialized = true;

        Canvas canvas = new Canvas(size.width,size.height);
        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle(Settings.get("vedge.window.name").asString());

        if(Settings.get("vedge.window.exit_on_close").asBoolean())
            primaryStage.setOnCloseRequest(e -> {
                System.exit(0);
            });

        AnimationTimer frameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                Frame.frame();
            }
        };
        frameTimer.start();

        primaryStage.show();

    }

    public static void go() {
        new Window();
        Application.launch();
    }

}
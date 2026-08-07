package org.py.vedge;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.awt.*;

public class Window extends Application {

    private static boolean windowInitialized = false;

    public static Graphics graphics;

    public static Point position = new Point(
            Settings.get("vedge.window.start_position.x").asInteger(),
            Settings.get("vedge.window.start_position.y").asInteger()
    );
    public static Dimension size = new Dimension(
            Settings.get("vedge.window.start_size.w").asInteger(),
            Settings.get("vedge.window.start_size.h").asInteger()
    );

    public static Stage stage;
    public static Scene scene;
    public static Pane root;
    public static Canvas canvas;

    public static boolean renderBackground = true;

    @Override
    public void start(Stage stage) { // TODO: on close, exit or something

        if(windowInitialized) return;
        else windowInitialized = true;

        Pane root = new Pane();
        Canvas canvas = new Canvas(size.width,size.height);

        root.getChildren().add(canvas);
        Scene scene = new Scene(root,size.width,size.height,Color.WHITE);

        stage.setTitle(Settings.get("vedge.window.name").asString());
        stage.setScene(scene);

        Window.stage = stage;
        Window.scene = scene;
        Window.root = root;
        Window.canvas = canvas;

        if(Settings.get("vedge.window.exit_on_close").asBoolean())
            stage.setOnCloseRequest(e -> {
                System.exit(0);
            });

        AnimationTimer frameTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(renderBackground) {
                    canvas.getGraphicsContext2D().setFill(Color.WHITE);
                    canvas.getGraphicsContext2D().fillRect(0,0,size.width,size.width);
                }
                Frame.frame();
            }
        };
        frameTimer.start();

        graphics = new Graphics(canvas.getGraphicsContext2D());

        onInitFinishes.forEach(Runnable::run);

        stage.show();

    }

    public static void go() {
        new Window();
        Application.launch();
    }

    private static Sparry<Runnable> onInitFinishes = new Sparry<>();
    public static void addOnInitFinish(Runnable runnable) {
        onInitFinishes.add(runnable);
    }

}
package org.py.vedge.infa;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.py.vedge.graphics.Graphics;
import org.py.vedge.ii.Bounds;
import org.py.vedge.ii.Dimension;
import org.py.vedge.ii.Position;
import org.py.vedge.util.Sparry;

public class Window extends Application {

    private static boolean windowInitialized = false;

    public static org.py.vedge.graphics.Graphics graphics;

    public static final Position position = new Position(0,0);
    public static Position windowPosition = new Position(
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

    public static Bounds getBounds() {
        return new Bounds(
                position,
                size
        );
    }

    @Override
    public void start(Stage stage) {

        stage.setX(windowPosition.x);
        stage.setY(windowPosition.y);

        if(windowInitialized) return;
        else windowInitialized = true;

        Pane root = new Pane();
        Canvas canvas = new Canvas(size.w,size.h);

        root.getChildren().add(canvas);
        Scene scene = new Scene(root,size.w,size.h,Color.WHITE);

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
                    canvas.getGraphicsContext2D().fillRect(0,0,size.w,size.h);
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
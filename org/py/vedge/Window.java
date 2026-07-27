package org.py.vedge;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.*;

public class Window extends Application {

    private static boolean windowInitialized = false;

    public static final JSONObject windowConfig = JSONFile.file("vedge_main").o("window");

    public static Point position = new Point(
            windowConfig.get("start_size.x").asInteger(),
            windowConfig.get("start_size.y").asInteger()
    );
    public static Dimension size = new Dimension(
            windowConfig.get("start_size.w").asInteger(),
            windowConfig.get("start_size.h").asInteger()
    );

    @Override
    public void start(Stage primaryStage) { // TODO: on close, exit or something

        if(windowInitialized) return;
        else windowInitialized = true;

        Canvas canvas = new Canvas(size.width,size.height);
        StackPane root = new StackPane(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle(windowConfig.get("name").asString());

        if(windowConfig.get("exit_on_close").asBoolean())
            primaryStage.setOnCloseRequest(e -> {
                System.exit(0);
            });



        primaryStage.show();

    }

    public static void go() {
        new Window();
        Application.launch();
    }

}
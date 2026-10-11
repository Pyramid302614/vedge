package org.py.vedge.infa.external;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class FX extends Application {

    public static Stage stage;
    public static Scene scene;
    public static Pane root;
    public static Canvas canvas;

    @Override
    public void start(Stage stage) {
        Pane root = new Pane();
        Canvas canvas = new Canvas(Window.vg.size.w,Window.vg.size.h);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root,Window.vg.size.w,Window.vg.size.h,Color.WHITE);
        stage.setScene(scene);
        FX.stage = stage;
        FX.scene = scene;
        FX.root = root;
        FX.canvas = canvas;
        Window.vg.go();
    }

    public void launch() {
        Application.launch();
    }

}

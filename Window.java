import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Window {

    public static Application application;
    public static Stage primaryStage;
    public static Scene scene;

    public static void go() {

        application = new Application() {
            @Override
            public void start(Stage primaryStage) {

//                Group root = new Group()
//                scene = new Scene(root);
//
//                Window.primaryStage = primaryStage;
//                Window.scene = scene;

            }
        };

    }

}
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            VBox root = new VBox();
            NewProjectScene scene = new NewProjectScene(root, 500, 500);
            primaryStage.setMinWidth(500);
            primaryStage.setMinHeight(500);
            primaryStage.setTitle("Anteater V0.01");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

public class Main extends Application { // inherit application to allow usage in Main class
    private static final int DIMENSION = 500;

    @Override
    public void start(Stage primaryStage) {
        try {
            /*
             * Below line of code creates the home screen, object with a VBOX layout
             * The default dimensions for this application are 500x500 therefor a constant will be used
             */
            HomeScreen home = new HomeScreen(primaryStage, new VBox(), DIMENSION, DIMENSION); 
            primaryStage.setMinWidth(DIMENSION);
            primaryStage.setMinHeight(DIMENSION);
            primaryStage.setTitle("Anteater V0.2");
            primaryStage.setScene(home);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args); // Inherited method to show the screen
    }
}

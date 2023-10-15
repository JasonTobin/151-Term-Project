package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

// Confirmation scene to cut down on code resuage / readability
// Pop-up box to confirm loss of data

public class ConfirmScene extends Scene {

    private String text;

    
    // sets default window for the scene.
    public ConfirmScene(Stage primaryStage, Pane root, String t) {
        super(root, 300, 100);
        this.text = t;
        Stage sureStage = new Stage();
        sureStage.setMinWidth(300);
        sureStage.setMinHeight(100);
        sureStage.setScene(this);
        sureStage.setResizable(false);
        sureStage.setTitle("Confirm");

        // set label dimensions
        Label confirmText = new Label(text);
        
        // sets the font preference and size
        Font font = Font.font("Segoe UI", FontWeight.NORMAL, FontPosture.REGULAR, 15);
        confirmText.setFont(font);
        confirmText.setTextFill(Color.BLACK);
        confirmText.setMinWidth(this.getWidth());
        confirmText.setAlignment(Pos.TOP_CENTER);
        confirmText.setTranslateY(5);
        root.getChildren().add(confirmText);

        sureStage.show();

        // set button dimensions
        Button conButton = new Button("Confirm");
        conButton.setFont(font);
        conButton.setTranslateY(25);
        conButton.setTranslateX(this.getWidth() / 5);
        conButton.setOnAction(event -> {
            sureStage.close();
            
            // sets the home screen
            primaryStage.setScene(new HomeScreen(primaryStage, new VBox(), Main.DIMENSION, Main.DIMENSION));
        });
        root.getChildren().add(conButton);

        // sets cancel button dimensions 
        Button cancelButton = new Button("Cancel");
        cancelButton.setFont(font);
        cancelButton.setTranslateY(-5);
        cancelButton.setTranslateX(3 * this.getWidth() / 5);
        cancelButton.setOnAction(event -> {
            sureStage.close();
        });
        root.getChildren().add(cancelButton);

    }
}

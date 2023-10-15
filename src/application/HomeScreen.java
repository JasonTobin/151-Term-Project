package application;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomeScreen extends Scene {
    public HomeScreen(Stage primaryStage, Pane root, double width, double height) {
        super(root, width, height);
        Label projLabel = new Label("Home");
        Font font = Font.font("Segoe UI", FontWeight.BOLD, FontPosture.REGULAR, 25);
        projLabel.setTextFill(Color.BLACK);
        projLabel.setFont(font);
        projLabel.setMinWidth(width);
        projLabel.setAlignment(Pos.TOP_CENTER);
        // projLabel.setTranslateY(25);
        root.getChildren().add(projLabel);

        Button searchButton = new Button("Search Projects");
        Font buttonFont = Font.font("Segoe UI", FontWeight.BOLD, FontPosture.REGULAR, 20);
        searchButton.setFont(buttonFont);
        searchButton.setTranslateY(85);
        searchButton.setMaxSize(300, 90);
        searchButton.setTranslateX((this.getWidth() - 300) / 2);
        searchButton.setOnAction(event -> {
            // Go to edit project sceen where project and ticket show up
            primaryStage.setScene(new SearchScene(primaryStage, new VBox(), width, height));
        });

        Button newProjButton = new Button("New Project");
        newProjButton.setId("newProj");
        newProjButton.setFont(buttonFont);
        newProjButton.setTranslateY(80);
        newProjButton.setMaxSize(300, 90);
        newProjButton.setTranslateX((this.getWidth() - 300) / 2);
        newProjButton.setOnAction(event -> {
            // Go to new project screen
            primaryStage.setScene(new NewProjectScene(primaryStage, new VBox(), width, height));
        });

        root.getChildren().add(newProjButton);
        root.getChildren().add(searchButton);

        this.widthProperty().addListener((observable, oldValue, newValue) -> { // Listeners for window size change to
                                                                               // variably move compenents
            projLabel.setMinWidth(this.getWidth());
            searchButton.setTranslateX((this.getWidth() - 300) / 2);
            newProjButton.setTranslateX((this.getWidth() - 300) / 2);
        });
    }
}

package application;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Home extends Pane {

    // width 450
    // height 600

    public Home() {
        super();

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        // Page Name
        Label homeLabel = new Label("Home");
        Font font = Font.font("Segoe UI", FontWeight.BOLD, FontPosture.REGULAR, 25);
        homeLabel.setTextFill(Color.BLACK);
        homeLabel.setFont(font);

        // Set the label's position to center it
        homeLabel.setMinWidth(450);
        homeLabel.setAlignment(Pos.TOP_CENTER);

        layout.getChildren().add(homeLabel);

        Button newProjButton = new Button("New Project");
        Font buttonFont = Font.font("Segoe UI", FontWeight.NORMAL, FontPosture.REGULAR, 20);
        newProjButton.setFont(buttonFont);
        newProjButton.setAlignment(Pos.TOP_CENTER);
        newProjButton.setTranslateY(60);
        newProjButton.setOnAction(event -> {
            // Go to new project screen
            Main.control.setNewProj();
        });

        layout.getChildren().add(newProjButton);

        this.getChildren().add(layout);
    }

}

package application;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class EditProjScreen extends Scene {
    public EditProjScreen(Stage primaryStage, Pane root, double width, double height, Project currProject) {
        super(root, width, height);

        // Text at top of application to signifiy page
        Label projLabel = new Label("Edit Project");
        Font font = Font.font("Segoe UI", FontWeight.BOLD, FontPosture.REGULAR, 25);
        projLabel.setTextFill(Color.BLACK);
        projLabel.setFont(font);
        projLabel.setMinWidth(width);
        projLabel.setAlignment(Pos.TOP_CENTER);
        root.getChildren().add(projLabel);

        // Name text field
        TextField enterName = new TextField();
        enterName.setText(currProject.getProjName());
        enterName.setFocusTraversable(false);
        enterName.setMaxSize(300, 30);
        enterName.setTranslateX((this.getWidth() - 300) / 2);
        enterName.setTranslateY(20);
        root.getChildren().add(enterName);

        // Editable date picker
        DatePicker dPicker = new DatePicker();
        dPicker.setValue(currProject.getProjDate());
        dPicker.setFocusTraversable(false);
        dPicker.setMaxSize(300, 30);
        dPicker.setTranslateY(40);
        dPicker.setTranslateX((this.getWidth() - 300) / 2);
        root.getChildren().add(dPicker);

        // Area to describe project
        TextArea projectDesc = new TextArea();
        projectDesc.setText(currProject.getProjDesc());
        projectDesc.setWrapText(true);
        projectDesc.setFocusTraversable(false);
        projectDesc.setTranslateY(60);
        projectDesc.setMaxSize(300, 150);
        projectDesc.setTranslateX((this.getWidth() - 300) / 2);
        root.getChildren().add(projectDesc);

        // Area to save button
        Button saveButton = new Button("Save"); // This is save button for the project, it will update the current project
        saveButton.setTranslateY(80);
        saveButton.setMaxSize(100, 30);
        saveButton.setTranslateX(((this.getWidth() - 100)) / 2);
        saveButton.setOnAction(event -> {
            if (enterName.getText().equals("")) { // TODO: Add duplicate protection for names
                if(!enterName.getText().equals(currProject.getProjName())) {
                    currProject.setProjName(enterName.getText());  // Update the project if the inputed values are not equal to saved values
                }
                if(!dPicker.getValue().isEqual(currProject.getProjDate())) {
                    currProject.setProjDate(dPicker.getValue());
                }
                if(!projectDesc.getText().equals(currProject.getProjDesc())) {
                    currProject.setProjDesc(projectDesc.getText());
                }
            } 
        });

        Button deleteButton = new Button("Delete"); // TODO: Add functionality to delete button
        deleteButton.setMaxSize(100, 30);
        deleteButton.setTranslateX((this.getWidth() - 100) / 2);
        deleteButton.setTranslateY(100);

        root.getChildren().add(saveButton);
        root.getChildren().add(deleteButton);

        this.widthProperty().addListener((observable, oldValue, newValue) -> { // Listeners for window size change to variably move compenents 
            projLabel.setMinWidth(this.getWidth());
            enterName.setTranslateX((this.getWidth() - 300) / 2);
            dPicker.setTranslateX((this.getWidth() - 300) / 2);
            projectDesc.setTranslateX((this.getWidth() - 300) / 2);
            saveButton.setTranslateX((this.getWidth() - 100) / 2);
        });
    }
}

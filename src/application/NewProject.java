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
import java.time.LocalDate;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.sql.*;

public class NewProject extends Pane {

    public NewProject() {
        super();

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        // Page Name
        Label newProjLabel = new Label("New Project");
        Font font = Font.font("Segoe UI", FontWeight.BOLD, FontPosture.REGULAR, 25);
        newProjLabel.setTextFill(Color.BLACK);
        newProjLabel.setFont(font);
        // Set the label's position to center it
        newProjLabel.setMinWidth(450);
        newProjLabel.setAlignment(Pos.TOP_CENTER);
        layout.getChildren().add(newProjLabel);

        // Text Field to for name of project
        TextField enterName = new TextField();
        enterName.setPromptText("Project Name");
        enterName.setFocusTraversable(false);
        enterName.setMinSize(300, 40);
        Font customFont = Font.font("Arial", 18);
        enterName.setStyle("-fx-font-size: " + customFont.getSize());
        enterName.setMaxSize(300, 40);
        enterName.setTranslateY(20);
        layout.getChildren().add(enterName);

        // Mutable date picker for object
        DatePicker dPicker = new DatePicker();
        dPicker.setValue(LocalDate.now());
        dPicker.setFocusTraversable(false);
        dPicker.setMaxSize(300, 40);
        dPicker.setMinSize(300, 40);
        dPicker.setTranslateY(40);
        layout.getChildren().add(dPicker);

        // Text area with prompt text
        TextArea projectDesc = new TextArea();
        projectDesc.setPromptText("Project Description");
        projectDesc.setWrapText(true);
        projectDesc.setFocusTraversable(false);
        projectDesc.setTranslateY(60);
        projectDesc.setMaxSize(300, 200);
        layout.getChildren().add(projectDesc);

        // Save button to create new object
        Button saveButton = new Button("Save");
        Font buttonFont = Font.font("Segoe UI", FontWeight.NORMAL, FontPosture.REGULAR, 20);
        saveButton.setTranslateY(80);
        saveButton.setTranslateX(-50);
        saveButton.setFont(buttonFont);
        saveButton.setOnAction(event -> {
            if (enterName.getText().isEmpty() || dPicker.getValue().equals(null)) { // TODO: Add duplicate value
                // protection for project names
                System.out.println("Please enter project information"); // Objects must have a nonempty name
                // TODO: Popup box for description
            }
            if (ProjectList.containsName(enterName.getText())) { // I do not understand why this code works, should it
                                                                 // not be !containsName() ???
                System.out.println("Name already found, please enter in correct name");
            } else {
                // Editable date and can have empty description
                Project createdProj = new Project(enterName.getText(), dPicker.getValue(), projectDesc.getText());

                ProjectList.AddProject(createdProj);

                try {
                    Statement stmt = Main.conn.createStatement();
                    stmt.executeUpdate(
                            "INSERT INTO tbl_projects (project_name, project_date, project_desc) VALUES ('" +
                                    createdProj.getProjName() + "','" + createdProj.getProjDate().toString() + "','"
                                    + createdProj.getProjDesc() + "')");

                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("Clicked!");
                Main.control.setDisplayProj(createdProj);
                Main.control.addToScroll(createdProj);
            }
        });
        layout.getChildren().add(saveButton);

        // Save button to create new object
        Button cancelButton = new Button("Cancel");
        cancelButton.setTranslateY(42);
        cancelButton.setTranslateX(50);
        cancelButton.setFont(buttonFont);
        cancelButton.setOnAction(event -> {
            System.out.println("Clicked!");
            Main.control.setHome();
        });
        layout.getChildren().add(cancelButton);

        this.getChildren().add(layout);
    }

}

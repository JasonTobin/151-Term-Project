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
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.sql.*;

public class NewProjectScene extends Scene {
    public NewProjectScene(Stage primaryStage, Pane root, double width, double height) {
        super(root, width, height);

        // Page name
        Label projLabel = new Label("New Project");
        Font font = Font.font("Segoe UI", FontWeight.BOLD, FontPosture.REGULAR, 25);
        projLabel.setTextFill(Color.BLACK);
        projLabel.setFont(font);
        projLabel.setMinWidth(width);
        projLabel.setAlignment(Pos.TOP_CENTER);
        root.getChildren().add(projLabel);

        // Text Field to for name of project
        TextField enterName = new TextField();
        enterName.setPromptText("Project Name");
        enterName.setFocusTraversable(false);
        enterName.setMaxSize(300, 30);
        enterName.setTranslateX((this.getWidth() - 300) / 2);
        enterName.setTranslateY(20);
        root.getChildren().add(enterName);

        // Mutable date picker for object
        DatePicker dPicker = new DatePicker();
        dPicker.setValue(LocalDate.now());
        dPicker.setFocusTraversable(false);
        dPicker.setMaxSize(300, 30);
        dPicker.setTranslateY(40);
        dPicker.setTranslateX((this.getWidth() - 300) / 2);
        root.getChildren().add(dPicker);

        // Text area with prompt text
        TextArea projectDesc = new TextArea();
        projectDesc.setPromptText("Project Description");
        projectDesc.setWrapText(true);
        projectDesc.setFocusTraversable(false);
        projectDesc.setTranslateY(60);
        projectDesc.setMaxSize(300, 150);
        projectDesc.setTranslateX((this.getWidth() - 300) / 2);
        root.getChildren().add(projectDesc);

        // Save button to create new object
        Button saveButton = new Button("Save");
        saveButton.setTranslateY(80);
        saveButton.setMaxSize(100, 30);
        saveButton.setTranslateX((this.getWidth() - 100) / 2);
        saveButton.setOnAction(event -> { // Listener for click of button
            if (enterName.getText().isEmpty() || dPicker.getValue().equals(null)) { // TODO: Add duplicate value
                                                                                    // protection for project names
                System.out.println("Please enter project information"); // Objects must have a nonempty name
                // TODO: Popup box for description
            } else {
                // Editable date and can have empty description
                Project createdProj = new Project(enterName.getText(), dPicker.getValue(), projectDesc.getText());

                ProjectList.AddProject(createdProj); // TODO: create SQL container for projects. ***CURRENT
                                                     // IMPLEMENTATION

                try {
                    Statement stmt = Main.conn.createStatement();
                    stmt.executeUpdate(
                            "INSERT INTO tbl_projects (project_name, project_date, project_desc) VALUES ('" +
                                    createdProj.getProjName() + "','" + createdProj.getProjDate().toString() + "','"
                                    + createdProj.getProjDesc() + "')");
                    Main.loadData();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("Clicked!");
                primaryStage.setScene(new EditProjScreen(primaryStage, new VBox(), width, height, createdProj));
            }
        });
        root.getChildren().add(saveButton);

        Button homeButton = new Button("⌂");
        homeButton.setLayoutX(0);
        Font homeFont = Font.font("Segoe UI", FontWeight.BOLD, FontPosture.REGULAR, 20);
        homeButton.setFont(homeFont);
        root.getChildren().add(homeButton);
        homeButton.setTranslateY(-255);
        homeButton.setMaxSize(30, 30);
        homeButton.setOnAction(event -> {
            ConfirmScene confirmWindow = new ConfirmScene(primaryStage, new VBox(),
                    "Any unsaved data will be lost!");
        });

        this.widthProperty().addListener((observable, oldValue, newValue) -> { // Listeners for window size change to
                                                                               // variably move compenents
            projLabel.setMinWidth(this.getWidth());
            enterName.setTranslateX((this.getWidth() - 300) / 2);
            dPicker.setTranslateX((this.getWidth() - 300) / 2);
            projectDesc.setTranslateX((this.getWidth() - 300) / 2);
            saveButton.setTranslateX((this.getWidth() - 100) / 2);
        });
    }
}

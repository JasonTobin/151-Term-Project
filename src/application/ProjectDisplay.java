package application;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class ProjectDisplay extends Pane {

    public ProjectDisplay(Project p) {
        super();

        VBox layout = new VBox();
        layout.setAlignment(Pos.TOP_CENTER);
        // Page Name
        Label newProjLabel = new Label("Project");
        Font font = Font.font("Segoe UI", FontWeight.BOLD, FontPosture.REGULAR, 25);
        newProjLabel.setTextFill(Color.BLACK);
        newProjLabel.setFont(font);
        // Set the label's position to center it
        newProjLabel.setMinWidth(450);
        newProjLabel.setAlignment(Pos.TOP_CENTER);

        layout.getChildren().add(newProjLabel);

        // Text Field to for name of project
        TextField enterName = new TextField();
        enterName.setEditable(false);
        enterName.setText(p.getProjName());
        enterName.setFocusTraversable(false);
        enterName.setMinSize(300, 40);
        Font customFont = Font.font("Arial", 18);
        enterName.setStyle("-fx-font-size: " + customFont.getSize());
        enterName.setMaxSize(300, 40);
        enterName.setTranslateY(20);
        layout.getChildren().add(enterName);

        // Uneditable date picker for object
        DatePicker dPicker = new DatePicker();
        dPicker.setEditable(false);
        dPicker.setDisable(true);
        dPicker.setStyle("-fx-opacity: 1");
        dPicker.getEditor().setStyle("-fx-opacity: 1"); // Set disable is annoying in it's properties, change style to
                                                        // look normal
        dPicker.setValue(p.getProjDate());
        dPicker.setEditable(false);
        dPicker.setFocusTraversable(false);
        dPicker.setMaxSize(300, 40);
        dPicker.setMinSize(300, 40);
        dPicker.setTranslateY(40);
        layout.getChildren().add(dPicker);

        // Text area with prompt text
        TextArea projectDesc = new TextArea();
        projectDesc.setEditable(false);
        projectDesc.setText(p.getProjDesc());
        projectDesc.setWrapText(true);
        projectDesc.setFocusTraversable(false);
        projectDesc.setTranslateY(60);
        projectDesc.setMaxSize(300, 200);
        layout.getChildren().add(projectDesc);

        Button newTicket = new Button("New Ticket");
        newTicket.setTranslateY(80);
        // newTicket.setTranslateX(50);
        newTicket.setFont(customFont);
        newTicket.setOnAction(event -> {
            System.out.println("Clicked!");
            Main.control.setNewTicket();
        });
        layout.getChildren().add(newTicket);

        Button editButton = new Button("Edit");
        editButton.setTranslateY(100);
        editButton.setFont(customFont);
        editButton.setOnAction(event -> {
            System.out.println("edit button clicked");
            Main.control.setEditProject(p);
        });
        layout.getChildren().add(editButton);

        Button deleteButton = new Button("Delete");
        deleteButton.setTranslateY(120);
        deleteButton.setFont(customFont);
        deleteButton.setOnAction(event -> {
            Stage sureStage = new Stage();
            sureStage.setWidth(300); // Set the initial width
            sureStage.setHeight(150); // Set the initial height
            sureStage.show();

            VBox root = new VBox();
            root.setPrefWidth(300); // Set the preferred width
            root.setPrefHeight(200); // Set the preferred height

            sureStage.setScene(new Scene(root));
            sureStage.setResizable(false);
            sureStage.setTitle("Confirm Deletion");

            // Label prompting the user
            Label confirmText = new Label("Deleting a project is permenant");
            confirmText.setTranslateY(10);
            confirmText.setFont(customFont);
            confirmText.setTextFill(Color.BLACK);
            confirmText.setMinWidth(sureStage.getWidth());
            confirmText.setAlignment(Pos.CENTER);
            confirmText.setTranslateX(-5);
            root.getChildren().add(confirmText);

            Button confirmButton = new Button("Confirm");
            confirmButton.setTranslateY(30);
            confirmButton.setTranslateX(50);
            confirmButton.setFont(customFont);
            confirmButton.setAlignment(Pos.CENTER_LEFT);
            confirmButton.setOnAction(clicked -> {
                sureStage.close();
                ProjectList.delete(p); // Delete current project from list
                Main.control.updateScrollProj(); // Update UI
                Main.control.setHome(); // return to home

                // TODO: Find out if deleting a project should also delete any related tickets
                // from proffessor

                // Update SQL
                try {
                    String deleteQuery = "DELETE FROM tbl_projects WHERE id = ?";
                    PreparedStatement preparedStatement = Main.conn.prepareStatement(deleteQuery);
                    preparedStatement.setInt(1, p.getID());
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        System.out.println("Project Successfully deleted");
                    } else {
                        System.out.println("Project not round or could not be deleted");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            root.getChildren().add(confirmButton);

            Button cancelButton = new Button("Cancel");
            cancelButton.setTranslateY(-3);
            cancelButton.setTranslateX(150);
            cancelButton.setFont(customFont);
            cancelButton.setOnAction(clicked -> {
                sureStage.close();
            });
            root.getChildren().add(cancelButton);
        });
        layout.getChildren().add(deleteButton);

        this.getChildren().add(layout);
    }

}

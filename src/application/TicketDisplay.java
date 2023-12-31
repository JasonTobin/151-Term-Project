package application;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.sql.*;

public class TicketDisplay extends Pane {

    public TicketDisplay(Ticket t) {
        super();

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        // Page Name
        Label newProjLabel = new Label("Ticket");
        Font font = Font.font("Segoe UI", FontWeight.BOLD, FontPosture.REGULAR, 25);
        newProjLabel.setTextFill(Color.BLACK);
        newProjLabel.setFont(font);
        // Set the label's position to center it
        newProjLabel.setMinWidth(450);
        newProjLabel.setAlignment(Pos.TOP_CENTER);
        layout.getChildren().add(newProjLabel);

        Font customFont = Font.font("Arial", 18);

        // Drop down box for projects
        ComboBox<String> dropDown = new ComboBox<String>(); // Change to text area?
        for (Project p : ProjectList.getList()) {
            dropDown.getItems().add(p.getProjName());
        }
        dropDown.setPromptText(t.getTicketProject());
        dropDown.setTranslateY(20);
        dropDown.setMinSize(300, 40);
        dropDown.setEditable(false);
        dropDown.setMouseTransparent(true);
        dropDown.setStyle("-fx-opacity: 1");
        layout.getChildren().add(dropDown);

        // Text Field to for name of project
        TextField enterName = new TextField();
        enterName.setEditable(false);
        enterName.setText(t.getBugName());
        enterName.setFocusTraversable(false);
        enterName.setMinSize(300, 40);
        enterName.setStyle("-fx-font-size: " + customFont.getSize());
        enterName.setMaxSize(300, 40);
        enterName.setTranslateY(40);
        layout.getChildren().add(enterName);

        // Mutable date picker for object
        DatePicker dPicker = new DatePicker();
        dPicker.setEditable(false);
        dPicker.setDisable(true);
        dPicker.setStyle("-fx-opacity: 1");
        dPicker.getEditor().setStyle("-fx-opacity: 1"); // Set disable is annoying in it's properties, change style to
                                                        // look normal
        dPicker.setValue(t.getBugDate());
        dPicker.setFocusTraversable(false);
        dPicker.setMaxSize(300, 40);
        dPicker.setMinSize(300, 40);
        dPicker.setTranslateY(60);
        layout.getChildren().add(dPicker);

        // Text area with prompt text
        TextArea projectDesc = new TextArea();
        projectDesc.setEditable(false);
        projectDesc.setText(t.getBugDesc());
        projectDesc.setWrapText(true);
        projectDesc.setFocusTraversable(false);
        projectDesc.setTranslateY(80);
        projectDesc.setMaxSize(300, 200);
        layout.getChildren().add(projectDesc);

        ScrollPane commentPane = new ScrollPane();
        commentPane.setTranslateY(100);
        commentPane.setMinHeight(120);
        commentPane.setMaxWidth(300);
        commentPane.setMaxHeight(120);

        VBox commentHolder = new VBox(); // gridpane might be better
        commentHolder.setSpacing(1);
        commentHolder.setMinHeight(150);
        for (String s : t.getComList()) {
            CommentBox cB = new CommentBox(s);
            commentHolder.getChildren().add(cB);
        }

        commentPane.setContent(commentHolder);

        layout.getChildren().add(commentPane);

        /*
         * Although following code looks complex, it serves a few purposes
         * - A UI prompt to allow the user to enter in a comment
         * - Although not neccessary, in order to stop submenu after submenu, popup box
         * is used
         */
        Font lowerButtonFont = Font.font("Arial", 18);
        Button addComment = new Button("Add Comment");
        Font buttonFont = Font.font("Segoe UI", FontWeight.NORMAL, FontPosture.REGULAR, 20);
        addComment.setFont(lowerButtonFont);
        addComment.setAlignment(Pos.TOP_CENTER);
        addComment.setTranslateY(106);
        addComment.setTranslateX(-80);
        addComment.setOnAction(event -> {
            // Go to new comment screen
            Stage sureStage = new Stage();
            sureStage.setWidth(300); // Set the initial width
            sureStage.setHeight(200); // Set the initial height

            VBox root = new VBox();
            root.setPrefWidth(300); // Set the preferred width
            root.setPrefHeight(200); // Set the preferred height

            sureStage.setScene(new Scene(root));
            sureStage.setResizable(false);
            sureStage.setTitle("Comment");

            // Label prompting the user
            Label confirmText = new Label("Enter Comment");
            confirmText.setTranslateY(10);
            confirmText.setFont(buttonFont);
            confirmText.setTextFill(Color.BLACK);
            confirmText.setMaxWidth(200);

            // User text entry
            TextArea commArea = new TextArea();
            commArea.setTranslateY(20);
            commArea.setPrefSize(200, 100);

            Button conButton = new Button("Confirm");
            conButton.setFont(buttonFont);
            conButton.setTranslateY(29);
            conButton.setTranslateX(20);
            conButton.setOnAction(randEvent -> {
                String text = commArea.getText();
                t.AddComment(text);
                String comments = t.getComments();

                String updateSql = "UPDATE tbl_tickets SET ticket_comms = ? WHERE ticket_name = ?";

                // Pass the new comment to sql to update SQLite container with new comment
                // Allows the saving of components
                try (PreparedStatement pstmt = Main.conn.prepareStatement(updateSql)) {
                    pstmt.setString(1, comments); // Bind the comment text
                    pstmt.setString(2, t.getBugName()); // Bind the ticket name
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                // Showcase the comment in the bottom of the display
                CommentBox commentBox = new CommentBox(text);
                commentHolder.getChildren().add(commentBox); // Set the StackPane as content of the ScrollPane
                sureStage.close();
            });

            Button cancelButton = new Button("Cancel");
            cancelButton.setTranslateY(-8);
            cancelButton.setTranslateX(150);
            cancelButton.setFont(buttonFont);
            cancelButton.setOnAction(randEvent -> {
                sureStage.close();
            });

            root.getChildren().addAll(confirmText, commArea, conButton, cancelButton);

            sureStage.show();
        });
        layout.getChildren().add(addComment);

        Button editButton = new Button("Edit");
        editButton.setFont(lowerButtonFont);
        editButton.setAlignment(Pos.TOP_CENTER);
        editButton.setTranslateY(73);
        editButton.setTranslateX(31);
        editButton.setOnAction(action -> {
            Main.control.setEditTicket(t);
        });
        layout.getChildren().add(editButton);

        Button deleteButton = new Button("Delete");
        deleteButton.setFont(lowerButtonFont);
        deleteButton.setAlignment(Pos.TOP_CENTER);
        deleteButton.setTranslateY(40);
        deleteButton.setTranslateX(110);
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
            Label confirmText = new Label("Deleting a ticket is permenant");
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
                TicketList.delete(t); // Delete current project from list
                Main.control.updateScrollProj(); // Update UI
                Main.control.setHome(); // return to home

                // Update SQL
                try {
                    String deleteQueryTickets = "DELETE FROM tbl_tickets WHERE ticket_name  = ?";
                    PreparedStatement prepStatmentTickets = Main.conn.prepareStatement(deleteQueryTickets);
                    prepStatmentTickets.setString(1, t.getBugName());
                    prepStatmentTickets.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                }

                // Update Ticket UI
                Main.control.updateScrollTicket();

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

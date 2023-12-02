package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

// Box to display comments on ticketDisplay
public class CommentBox extends StackPane {
    private String comment;
    public boolean clicked = false;
    // private static int spacing = 0;
    private Rectangle rectangle;

    // Return rectangle in order to change color
    public Rectangle getRectangle() {
        return this.rectangle;
    }

    public CommentBox(String commentString) {
        this.comment = commentString;
        rectangle = new Rectangle(284, 30);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        this.setTranslateX(0);
        // this.setTranslateY(spacing);
        // spacing++;

        Text tabName = new Text(this.comment);
        Font font = Font.font("Segoe UI", FontWeight.NORMAL, FontPosture.REGULAR, 15);
        tabName.setFont(font);
        tabName.setTextAlignment(TextAlignment.LEFT);
        tabName.setWrappingWidth(rectangle.getWidth() - 10);

        double textHeight = tabName.getBoundsInLocal().getHeight();
        rectangle.setHeight(textHeight + 10);

        this.getChildren().addAll(rectangle, tabName);
    }

    public CommentBox(String commentString, boolean exists, Ticket t, VBox commentHolder) {
        this.comment = commentString;
        rectangle = new Rectangle(284, 30); // Default width, you can adjust as needed
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        this.setTranslateX(0);

        Text commentText = new Text(this.comment);
        Font font = Font.font("Segoe UI", FontWeight.NORMAL, FontPosture.REGULAR, 15);
        commentText.setFont(font);
        commentText.setTextAlignment(TextAlignment.LEFT);
        commentText.setWrappingWidth(rectangle.getWidth() - 40);
        commentText.setTranslateX(-15);

        // Calculate the height based on the wrapped text
        double textHeight = commentText.getBoundsInLocal().getHeight();
        rectangle.setHeight(textHeight + 10); // Add some padding

        // Create an "Edit" button
        Button editButton = new Button("Edit");
        Font buttonFont = Font.font("Segoe UI", FontWeight.NORMAL, FontPosture.REGULAR, 20);
        editButton.setAlignment(Pos.TOP_RIGHT);
        editButton.setTranslateX(-1);
        editButton.setTranslateY(1);
        editButton.setOnAction(action -> {
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
            Label confirmText = new Label("Edit Comment:");
            confirmText.setTranslateY(10);
            confirmText.setFont(buttonFont);
            confirmText.setTextFill(Color.BLACK);
            confirmText.setMaxWidth(200);

            // User text entry
            TextArea commArea = new TextArea();
            commArea.setTranslateY(20);
            commArea.setPrefSize(200, 100);
            commArea.setText(commentText.getText());

            Button saveButton = new Button("Save");
            saveButton.setFont(buttonFont);
            saveButton.setTranslateY(30);
            saveButton.setTranslateX(10);
            saveButton.setOnAction(randEvent -> {
                String text = commArea.getText();
                if (!text.equals(commentText.getText())) {
                    for (int i = 0; i < t.getComList().size(); i++) {
                        if (t.getComList().get(i).equals(commentText.getText())) {
                            t.setComment(i, text);
                            break;
                        }
                    }
                    commentText.setText(text);
                }
                sureStage.close();
            });

            Button cancelButton = new Button("Cancel");
            cancelButton.setTranslateY(-8);
            cancelButton.setTranslateX(90);
            cancelButton.setFont(buttonFont);
            cancelButton.setOnAction(randEvent -> {
                sureStage.close();
            });

            Button deleteButton = new Button("Delete");
            deleteButton.setTranslateY(-46);
            deleteButton.setTranslateX(190);
            deleteButton.setFont(buttonFont);
            deleteButton.setOnAction(randEvent -> {
                for (int i = 0; i < t.getComList().size(); i++) {
                    if (t.getComList().get(i).equals(commentText.getText())) {
                        t.removeComment(i);
                        break;
                    }
                }
                commentHolder.getChildren().remove(this);
                sureStage.close();
            });

            root.getChildren().addAll(confirmText, commArea, saveButton, cancelButton, deleteButton);

            sureStage.show();
        });

        // Position the button in the top-right corner of the rectangle
        StackPane.setAlignment(editButton, Pos.TOP_RIGHT);

        this.getChildren().addAll(rectangle, commentText, editButton);
    }
}

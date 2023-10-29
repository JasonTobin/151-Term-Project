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
        layout.getChildren().add(dropDown);

        // Text Field to for name of project
        TextField enterName = new TextField();
        enterName.setText(t.getBugName());
        enterName.setFocusTraversable(false);
        enterName.setMinSize(300, 40);
        enterName.setStyle("-fx-font-size: " + customFont.getSize());
        enterName.setMaxSize(300, 40);
        enterName.setTranslateY(40);
        layout.getChildren().add(enterName);

        // Mutable date picker for object
        DatePicker dPicker = new DatePicker();
        dPicker.setValue(LocalDate.now());
        dPicker.setFocusTraversable(false);
        dPicker.setMaxSize(300, 40);
        dPicker.setMinSize(300, 40);
        dPicker.setTranslateY(60);
        layout.getChildren().add(dPicker);

        // Text area with prompt text
        TextArea projectDesc = new TextArea();
        projectDesc.setText(t.getBugDesc());
        projectDesc.setWrapText(true);
        projectDesc.setFocusTraversable(false);
        projectDesc.setTranslateY(80);
        projectDesc.setMaxSize(300, 200);
        layout.getChildren().add(projectDesc);

        ScrollPane commentPane = new ScrollPane();
        commentPane.setTranslateY(100);
        commentPane.setMinHeight(120);

        VBox commentHolder = new VBox(); // gridpane might be better
        commentHolder.setSpacing(1);
        commentHolder.setMinHeight(151);
        for (String s : t.getList()) {
            CommentBox cB = new CommentBox(s);
            commentHolder.getChildren().add(cB);
        }

        commentPane.setContent(commentHolder);

        layout.getChildren().add(commentPane);

        Button addComment = new Button("Add Comment");
        Font buttonFont = Font.font("Segoe UI", FontWeight.NORMAL, FontPosture.REGULAR, 20);
        addComment.setFont(buttonFont);
        addComment.setAlignment(Pos.TOP_CENTER);
        addComment.setTranslateY(105);
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

            Label confirmText = new Label("Enter Comment");
            confirmText.setTranslateY(10);
            confirmText.setFont(buttonFont);
            confirmText.setTextFill(Color.BLACK);
            confirmText.setMaxWidth(200);

            TextArea commArea = new TextArea();
            commArea.setTranslateY(20);
            commArea.setPrefSize(200, 100);

            Button conButton = new Button("Confirm");
            conButton.setFont(buttonFont);
            conButton.setTranslateY(30);
            conButton.setTranslateX(20);
            conButton.setOnAction(randEvent -> {
                String text = commArea.getText();
                t.AddComment(text);
                String comments = t.getComments();

                String updateSql = "UPDATE tbl_tickets SET ticket_comms = ? WHERE ticket_name = ?";

                try (PreparedStatement pstmt = Main.conn.prepareStatement(updateSql)) {
                    pstmt.setString(1, comments); // Bind the comment text
                    pstmt.setString(2, t.getBugName()); // Bind the ticket name
                    pstmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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

        this.getChildren().add(layout);
    }

}

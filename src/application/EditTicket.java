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

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.sql.*;

public class EditTicket extends Pane {

    public EditTicket(Ticket t) {
        super();

        Font buttonFont = Font.font("Segoe UI", FontWeight.NORMAL, FontPosture.REGULAR, 20);
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
        enterName.setEditable(true);
        enterName.setText(t.getBugName());
        enterName.setFocusTraversable(false);
        enterName.setMinSize(300, 40);
        enterName.setStyle("-fx-font-size: " + customFont.getSize());
        enterName.setMaxSize(300, 40);
        enterName.setTranslateY(40);
        layout.getChildren().add(enterName);

        // Mutable date picker for object
        DatePicker dPicker = new DatePicker();
        dPicker.setValue(t.getBugDate());
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
        commentPane.setMaxWidth(300);
        commentPane.setMaxHeight(120);

        VBox commentHolder = new VBox(); // gridpane might be better
        commentHolder.setSpacing(1);
        commentHolder.setMinHeight(150);
        for (String s : t.getComList()) {
            CommentBox cB = new CommentBox(s, true, t, commentHolder); // TODO: Add edit button overload
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

        Button saveButton = new Button("Save");
        saveButton.setFont(buttonFont);
        saveButton.setAlignment(Pos.TOP_CENTER);
        saveButton.setTranslateY(104);
        saveButton.setTranslateX(-50);
        saveButton.setOnAction(action -> {
            // Update ticket
            if (enterName.getText() != t.getBugName() && !TicketList.containsName(enterName.getText())
                    && enterName.getText() != null) {
                t.setBugName(enterName.getText());
            }
            if (dPicker.getValue() != t.getBugDate() && dPicker.getValue() != null && validDate(dPicker)) {
                t.setBugDate(dPicker.getValue());
            }
            if (projectDesc.getText() != t.getBugDesc()) {
                t.setBugDesc(projectDesc.getText());
            }
            if (dropDown.getValue() != t.getTicketProject() && dropDown.getValue() != null) {
                t.setTicketProjectName(dropDown.getValue());
            }

            // Update SQL
            try {
                PreparedStatement pstmt = Main.conn.prepareStatement(
                        "UPDATE tbl_tickets SET ticket_name=?, ticket_date=?, ticket_desc=?, ticket_proj=?, ticket_comms=? WHERE id=?");

                pstmt.setString(1, t.getBugName()); // Project is already updated so can be pulled from object
                pstmt.setString(2, t.getBugDate().toString());
                pstmt.setString(3, t.getBugDesc());
                pstmt.setString(4, t.getTicketProject());
                pstmt.setString(5, t.getComments());
                pstmt.setInt(6, t.getID());

                pstmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }

            Main.control.updateScrollTicket();
            Main.control.setDisplayTick(t);
        });
        layout.getChildren().add(saveButton);

        Button cancelEdit = new Button("Cancel");
        cancelEdit.setFont(buttonFont);
        cancelEdit.setAlignment(Pos.TOP_CENTER);
        cancelEdit.setTranslateY(66);
        cancelEdit.setTranslateX(30);
        cancelEdit.setOnAction(action -> {
            Main.control.setDisplayTick(t);
        });
        layout.getChildren().add(cancelEdit);

        this.getChildren().add(layout);
    }

    private boolean validDate(DatePicker d) {
        try {
            d.getConverter().fromString(d.getEditor().getText()); // if there is an exception it is not valid
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }
}

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
import javafx.scene.control.ComboBox;

import java.time.LocalDate;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.sql.*;

public class NewTicket extends Pane {

    public NewTicket() {
        super();

        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        // Page Name
        Label newProjLabel = new Label("New Ticket");
        Font font = Font.font("Segoe UI", FontWeight.BOLD, FontPosture.REGULAR, 25);
        newProjLabel.setTextFill(Color.BLACK);
        newProjLabel.setFont(font);
        // Set the label's position to center it
        newProjLabel.setMinWidth(450);
        newProjLabel.setAlignment(Pos.TOP_CENTER);
        layout.getChildren().add(newProjLabel);

        Font customFont = Font.font("Arial", 18);

        // Drop down box for projects
        ComboBox<String> dropDown = new ComboBox<String>();
        for (Project p : ProjectList.getList()) {
            dropDown.getItems().add(p.getProjName());
        }
        dropDown.setPromptText("Project Name");
        dropDown.setTranslateY(20);
        dropDown.setMinSize(300, 40);
        layout.getChildren().add(dropDown);

        // Text Field to for name of project
        TextField enterName = new TextField();
        enterName.setPromptText("Bug Name");
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
        projectDesc.setPromptText("Bug Description");
        projectDesc.setWrapText(true);
        projectDesc.setFocusTraversable(false);
        projectDesc.setTranslateY(80);
        projectDesc.setMaxSize(300, 200);
        layout.getChildren().add(projectDesc);

        // Save button to create new object
        Button saveButton = new Button("Save");
        Font buttonFont = Font.font("Segoe UI", FontWeight.NORMAL, FontPosture.REGULAR, 20);
        saveButton.setTranslateY(100);
        saveButton.setTranslateX(-50);
        saveButton.setFont(buttonFont);
        saveButton.setOnAction(event -> {
            if (enterName.getText().isEmpty() || dPicker.getValue().equals(null)) { // TODO: Add duplicate value
                // protection for project names
                System.out.println("Please enter project information"); // Objects must have a nonempty name
                // TODO: Popup box for description
            }
            if (TicketList.containsName(enterName.getText())) { // I do not understand why this code works, should it
                                                                // not be !containsName() ???
                System.out.println("Name already found, please enter in correct name");
            } else {
                // Editable date and can have empty description
                Ticket createdTick = new Ticket(enterName.getText(), dPicker.getValue(), projectDesc.getText(),
                        dropDown.getValue());

                TicketList.AddTicket(createdTick);

                try {
                    Statement stmt = Main.conn.createStatement();
                    stmt.executeUpdate(
                            "INSERT INTO tbl_tickets (ticket_name, ticket_date, ticket_desc, ticket_proj) VALUES ('" +
                                    createdTick.getBugName() + "','" + createdTick.getBugDate().toString() + "','"
                                    + createdTick.getBugDesc() + "','" + createdTick.getTicketProject() + "')");

                } catch (Exception e) {
                    e.printStackTrace();
                }

                System.out.println("Clicked!");
                Main.control.setDisplayTick(createdTick);
                Main.control.addToTicketScroll(createdTick);
            }
        });
        layout.getChildren().add(saveButton);

        // Save button to create new object
        Button cancelButton = new Button("Cancel");
        cancelButton.setTranslateY(62);
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

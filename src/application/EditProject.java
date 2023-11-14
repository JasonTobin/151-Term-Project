package application;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.DateTimeException;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class EditProject extends Pane {

    public EditProject(Project p) {
        super();

        VBox layout = new VBox();
        layout.setAlignment(Pos.TOP_CENTER);
        // Page Name
        Label newProjLabel = new Label("Edit Project");
        Font font = Font.font("Segoe UI", FontWeight.BOLD, FontPosture.REGULAR, 25);
        newProjLabel.setTextFill(Color.BLACK);
        newProjLabel.setFont(font);
        // Set the label's position to center it
        newProjLabel.setMinWidth(450);
        newProjLabel.setAlignment(Pos.TOP_CENTER);

        layout.getChildren().add(newProjLabel);

        // Text Field to for name of project
        TextField enterName = new TextField();
        enterName.setEditable(true);
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
        dPicker.setEditable(true);
        dPicker.setValue(p.getProjDate());
        dPicker.setEditable(false);
        dPicker.setFocusTraversable(false);
        dPicker.setMaxSize(300, 40);
        dPicker.setMinSize(300, 40);
        dPicker.setTranslateY(40);
        layout.getChildren().add(dPicker);

        // Text area with prompt text
        TextArea projectDesc = new TextArea();
        projectDesc.setEditable(true);
        projectDesc.setText(p.getProjDesc());
        projectDesc.setWrapText(true);
        projectDesc.setFocusTraversable(false);
        projectDesc.setTranslateY(60);
        projectDesc.setMaxSize(300, 200);
        layout.getChildren().add(projectDesc);

        Button saveButton = new Button("Save");
        saveButton.setTranslateY(80);
        // newTicket.setTranslateX(50);
        saveButton.setFont(customFont);
        saveButton.setOnAction(event -> {
            System.out.println("Save button Clicked!");
            String oldName = p.getProjName();
            // Update project
            if (enterName.getText() != p.getProjName() && !ProjectList.containsName(enterName.getText())
                    && enterName.getText() != null) {
                p.setProjName(enterName.getText());
            }
            if (dPicker.getValue() != p.getProjDate() && dPicker.getValue() != null && validDate(dPicker)) {
                p.setProjDate(dPicker.getValue());
            }
            if (projectDesc.getText() != p.getProjDesc()) {
                p.setProjDesc(projectDesc.getText());
            }

            // Update SQL
            try {
                PreparedStatement pstmt = Main.conn.prepareStatement(
                        "UPDATE tbl_projects SET project_name=?, project_date=?, project_desc=? WHERE id=?");

                pstmt.setString(1, p.getProjName()); // Project is already updated so can be pulled from object
                pstmt.setString(2, p.getProjDate().toString());
                pstmt.setString(3, p.getProjDesc());
                pstmt.setInt(4, p.getID());

                pstmt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace(); // Handle the exception appropriately
            }

            // Update Tickets Both In GUI and in SQL
            if (!p.getProjName().equals(oldName)) { // TODO: multithread all SQL updates to improve runtime
                for (Ticket someTicket : TicketList.getList()) {
                    if (someTicket.getTicketProject().equals(oldName)) {
                        someTicket.setTicketProjectName(p.getProjName());
                        try {
                            PreparedStatement pstmt = Main.conn.prepareStatement(
                                    "UPDATE tbl_tickets SET ticket_proj=? WHERE ticket_name=?");

                            pstmt.setString(1, p.getProjName()); // Project is already updated so can be pulled from
                                                                 // object
                            pstmt.setString(2, someTicket.getBugName());
                            pstmt.executeUpdate();

                        } catch (SQLException e) {
                            e.printStackTrace(); // Handle the exception appropriately
                        }
                    }
                }
            }

            // Re-sort list
            ProjectList.getList().sort(ProjectList.com);

            // Update GUI
            Main.control.updateScrollProj();
            Main.control.updateScrollTicket();

            // Return to Project Display
            Main.control.setDisplayProj(p);
        });
        layout.getChildren().add(saveButton);

        Button cancelButton = new Button("Cancel");
        cancelButton.setTranslateY(100);
        cancelButton.setFont(customFont);
        cancelButton.setOnAction(event -> {
            System.out.println("cancel button clicked");
            Main.control.setDisplayProj(p);
        });
        layout.getChildren().add(cancelButton);

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

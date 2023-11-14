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

        this.getChildren().add(layout);
    }

}

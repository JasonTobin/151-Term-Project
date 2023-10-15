package application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class SearchScene extends Scene {
    private List<Project> filteredItems = new ArrayList<>();
    private Project currentProj;
    boolean eBAdded = false;

    public SearchScene(Stage primaryStage, Pane root, double width, double height) {
        super(root, width, height); // VBox

        Label searchLabel = new Label("Search Projects");
        Font font = Font.font("Segoe UI", FontWeight.BOLD, FontPosture.REGULAR, 25);
        searchLabel.setTextFill(Color.BLACK);
        searchLabel.setFont(font);
        searchLabel.setMinWidth(width);
        searchLabel.setAlignment(Pos.TOP_CENTER);

        ComboBox<Project> dropDown = new ComboBox<>();
        dropDown.setEditable(true);

        dropDown.setMinWidth(width - 10);
        dropDown.setTranslateX(5);
        dropDown.setTranslateY(20);

        /*
         * TODO: Make combo box searchable. Code underneath does but it causes recursive
         * exceptions
         * dropDown.getEditor().textProperty().addListener((observable, oldValue,
         * newValue) -> {
         * String searchText = newValue.toLowerCase();
         * filteredItems.clear();
         * 
         * for (Project proj : ProjectList.getList()) {
         * if (proj.getProjName().toLowerCase().contains(searchText)) {
         * filteredItems.add(proj);
         * }
         * }
         * 
         * dropDown.getItems().setAll(filteredItems);
         * 
         * // dropDown.show();
         * });
         */

        dropDown.setCellFactory(param -> new ListCell<Project>() {
            @Override
            protected void updateItem(Project item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getProjName());
                }
            }
        });

        dropDown.setConverter(new StringConverter<Project>() {
            @Override
            public String toString(Project proj) {
                if (proj == null) {
                    return null;
                } else {
                    return proj.getProjName();
                }
            }

            @Override
            public Project fromString(String string) {
                // Needs implementation
                return null;
            }
        });

        dropDown.getItems().addAll(ProjectList.getList());

        // Name text field
        TextField pName = new TextField();
        pName.setText("");
        pName.setFocusTraversable(false);
        pName.setMaxSize(300, 30);
        pName.setTranslateX((this.getWidth() - 300) / 2);
        pName.setTranslateY(40);
        pName.setEditable(false);

        // uneditable date picker
        DatePicker dPicker = new DatePicker();
        dPicker.setValue(null);
        dPicker.setFocusTraversable(false);
        dPicker.setMaxSize(300, 30);
        dPicker.setTranslateY(60);
        dPicker.setEditable(false);
        dPicker.setDisable(true);
        dPicker.setTranslateX((this.getWidth() - 300) / 2);

        // Area to describe project
        TextArea projectDesc = new TextArea();
        projectDesc.setText("");
        projectDesc.setWrapText(true);
        projectDesc.setFocusTraversable(false);
        projectDesc.setTranslateY(80);
        projectDesc.setMaxSize(300, 150);
        projectDesc.setTranslateX((this.getWidth() - 300) / 2);
        projectDesc.setEditable(false);

        Button editButton = new Button("Edit");
        Font homeFont = Font.font("Segoe UI", FontWeight.BOLD, FontPosture.REGULAR, 20);
        editButton.setFont(homeFont);
        editButton.setMaxSize(100, 30);
        editButton.setTranslateX((this.getWidth() - 100) / 2);
        editButton.setTranslateY(100);
        editButton.setOnAction(event -> {
            primaryStage.setScene(new EditProjScreen(primaryStage, new VBox(), width, height, currentProj));
        });
        dropDown.setOnAction(event -> {
            Project selectedItem = dropDown.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                pName.setText(selectedItem.getProjName());
                dPicker.setValue(selectedItem.getProjDate());
                projectDesc.setText(selectedItem.getProjDesc());
                currentProj = selectedItem;
                if (!eBAdded) {
                    root.getChildren().add(editButton);
                    eBAdded = true;
                }
            }
        });

        Button homeButton = new Button("⌂");
        homeButton.setLayoutX(0);
        homeButton.setFont(homeFont);
        root.getChildren().add(homeButton);
        homeButton.setMaxSize(30, 30);
        homeButton.setOnAction(event -> {
            primaryStage.setScene(new HomeScreen(primaryStage, new VBox(), Main.DIMENSION, Main.DIMENSION));
        });

        root.getChildren().add(searchLabel);
        root.getChildren().add(dropDown);
        root.getChildren().add(pName);
        root.getChildren().add(dPicker);
        root.getChildren().add(projectDesc);

        this.widthProperty().addListener((observable, oldValue, newValue) -> { // Listeners for window size change to
            // variably move compenents
            searchLabel.setMinWidth(this.getWidth());
            dropDown.setTranslateX(((this.getWidth() - dropDown.getWidth()) / 2) + 5);
        });
    }
}

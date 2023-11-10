package application;

import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;

public class ProjectBox extends StackPane {
    private String name;
    private Project proj;
    public boolean clicked = false;
    private static int spacing = 0;
    private Rectangle rectangle;

    public Rectangle getRectangle() {
        return this.rectangle;
    }

    // Box to dispaly projects in
    // Own class to allow modular storage and sorting in later search feather
    // TODO: Add comparison capabilities in order to allow this to be searchable
    public ProjectBox(Project createdProj) {
        this.proj = createdProj;
        rectangle = new Rectangle(190, 50);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        this.setTranslateX(-10);
        // this.setTranslateY(spacing);
        spacing++;

        this.name = createdProj.getProjName();

        Text tabName = new Text(this.name);
        Font font = Font.font("Segoe UI", FontWeight.NORMAL, FontPosture.REGULAR, 15);
        tabName.setFont(font);

        this.getChildren().addAll(rectangle, tabName);

        // Show the user which box is clicked by changing the color
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (MainController.clickedBox != null) {
                    MainController.clickedBox.getRectangle().setFill(Color.WHITE);
                    getRectangle().setFill(Color.LIGHTGRAY);
                    MainController.clickedBox = ProjectBox.this;
                } else {
                    getRectangle().setFill(Color.LIGHTGRAY);
                    MainController.clickedBox = ProjectBox.this;
                }
                Main.control.setDisplayProj(createdProj);
            }

        });
    }
}

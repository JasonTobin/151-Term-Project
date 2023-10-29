package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.layout.StackPane;

public class CommentBox extends StackPane {
    private String comment;
    public boolean clicked = false;
    private static int spacing = 0;
    private Rectangle rectangle;

    public Rectangle getRectangle() {
        return this.rectangle;
    }

    public CommentBox(String commentString) {
        this.comment = commentString;
        rectangle = new Rectangle(475, 30);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        this.setTranslateX(-10);
        this.setTranslateY(spacing);
        spacing++;

        Text tabName = new Text(this.comment);
        Font font = Font.font("Segoe UI", FontWeight.NORMAL, FontPosture.REGULAR, 15);
        tabName.setFont(font);

        this.getChildren().addAll(rectangle, tabName);
    }
}

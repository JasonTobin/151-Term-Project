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

public class TicketBox extends StackPane {
    private String name;
    private Ticket tick;
    public boolean clicked = false;
    private Rectangle rectangle;

    public Rectangle getRectangle() {
        return this.rectangle;
    }

    // Ticket box similar to project box
    public TicketBox(Ticket createdTicket) {
        this.tick = createdTicket;
        rectangle = new Rectangle(181, 50);
        rectangle.setFill(Color.WHITE);
        rectangle.setStroke(Color.BLACK);
        this.setTranslateX(-11);
        // this.setTranslateY(spacing);

        this.name = createdTicket.getBugName();

        Text tabName = new Text(this.name);
        Font font = Font.font("Segoe UI", FontWeight.NORMAL, FontPosture.REGULAR, 15);
        tabName.setFont(font);

        this.getChildren().addAll(rectangle, tabName);

        // Show user which ticket is selected, just like project box
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (MainController.clickedTickBox != null) {
                    MainController.clickedTickBox.getRectangle().setFill(Color.WHITE);
                    getRectangle().setFill(Color.LIGHTGRAY);
                    MainController.clickedTickBox = TicketBox.this;
                } else {
                    getRectangle().setFill(Color.LIGHTGRAY);
                    MainController.clickedTickBox = TicketBox.this;
                }
                Main.control.setDisplayTick(createdTicket);
            }

        });
    }
}

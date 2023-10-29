package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainController {
    int scrollVBWidth = 539; // This is most likely subject to change in order to make visibility easier. For
                             // now keep

    public static ProjectBox clickedBox;
    public static TicketBox clickedTickBox;

    @FXML
    private ScrollPane scroolHolder;

    // Set the screen to the home of the applicaiton
    public void setHome() {
        ScrollPane scroolHolder = Main.control.scroolHolder;
        scroolHolder.setContent(new Home());
    }

    // New Project panel
    public void setNewProj() {
        ScrollPane scroolHolder = Main.control.scroolHolder;
        scroolHolder.setContent(new NewProject());
    }

    // New ticket panel
    public void setNewTicket() {
        ScrollPane scroolHolder = Main.control.scroolHolder;
        scroolHolder.setContent(new NewTicket());
    }

    // Display a selected propject
    public void setDisplayProj(Project createdProj) {
        ScrollPane scroolHolder = Main.control.scroolHolder;
        scroolHolder.setContent(new ProjectDisplay(createdProj));
    }

    // Display a selected ticket
    public void setDisplayTick(Ticket createdTicket) {
        ScrollPane scroolHolder = Main.control.scroolHolder;
        scroolHolder.setContent(new TicketDisplay(createdTicket));
    }

    @FXML
    private Pane paneHolder;

    @FXML
    private VBox scrollVBox;

    @FXML
    private ScrollPane projScroll;

    @FXML
    private ScrollPane ticketScroll;

    @FXML
    private VBox ticketVBox;

    // Add a new project to it's list on the sidebar
    public void addToScroll(Project createdProject) { // TODO: Add a search feature for these boxes
        VBox scrollVBox = Main.control.scrollVBox;
        ProjectBox newBox = new ProjectBox(createdProject);
        scrollVBox.getChildren().add(newBox);
        // Get the size after adding the ProjectBox
        if (ProjectList.getList().size() > 14) {
            scrollVBWidth += 18;
            scrollVBox.setMinSize(210, scrollVBWidth);
        }
        projScroll.layout(); // Force a layout pass
    }

    // Same as project search just with tickets
    public void addToTicketScroll(Ticket createdTicket) {
        VBox ticketVBox = Main.control.ticketVBox;
        ticketVBox.getChildren().add(new TicketBox(createdTicket)); // TODO: Create ticketbox
        ticketScroll.layout();
    }

    @FXML // For the new project button in top left
    private void handleButtonClick(ActionEvent event) {
        System.out.println("Clicked!");
        setNewProj();
    }

    @FXML // For the new ticket button in top right
    private void ticketButtonClick(ActionEvent event) {
        System.out.println("New Ticket Clicked");
        this.setNewTicket();
    }

    @FXML
    private TextField serachArea;

    // Initialize fxml components to access later on. Called on application open
    public void initialize() {
        if (serachArea == null) {
            serachArea = new TextField();
            serachArea.setPromptText("Search Projects");
        }
        if (projScroll == null) {
            projScroll = new ScrollPane();
        }
        if (scrollVBox == null) {
            scrollVBox = new VBox();
        }
        projScroll.setContent(scrollVBox);
        scrollVBox.setMinSize(204, 539);
        scrollVBox.setAlignment(Pos.TOP_LEFT);

        if (ticketScroll == null) {
            ticketScroll = new ScrollPane();
        }
        if (ticketVBox == null) {
            ticketVBox = new VBox();
        }
        ticketScroll.setContent(ticketVBox);
        ticketVBox.setMinSize(204, 539);
        ticketVBox.setAlignment(Pos.TOP_LEFT);
        System.out.println(this.scrollVBox.getWidth());
    }

}
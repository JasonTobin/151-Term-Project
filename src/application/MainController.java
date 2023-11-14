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

    public void setEditProject(Project p) {
        ScrollPane scroolHolder = Main.control.scroolHolder;
        scroolHolder.setContent(new EditProject(p));
    }

    @FXML
    private TextField searchArea;

    @FXML
    private VBox scrollVBox; // holder for project list display

    public void updateScrollProj() { // updates scrollVBox
        String curString = searchArea.getText();
        // System.out.println(curString); // ***Delete at later point: checking for
        // string

        scrollVBox = Main.control.scrollVBox; // Clear box so only displayed ones are with valid strings
        scrollVBox.getChildren().clear();

        for (int i = 0; i < ProjectList.getList().size(); i++) {
            if (ProjectList.getList().get(i).getProjName().toLowerCase().contains(curString.toLowerCase())) {
                addToScroll(ProjectList.getList().get(i));
            }
        }
    }

    @FXML
    private TextField searchTickets;

    @FXML
    private VBox ticketVBox;

    public void updateScrollTicket() {
        searchTickets = Main.control.searchTickets;
        String curString = searchTickets.getText();
        // System.out.println(curString); // ***Delete at later point: checking for
        // string

        ticketVBox = Main.control.ticketVBox; // Clear box so only displayed ones are with valid strings
        ticketVBox.getChildren().clear();

        for (int i = 0; i < TicketList.getList().size(); i++) {
            if (TicketList.getList().get(i).getBugName().toLowerCase().contains(curString.toLowerCase())) {
                addToTicketScroll(TicketList.getList().get(i));
            }
        }
    }

    @FXML
    private Pane paneHolder;

    @FXML
    private ScrollPane projScroll;

    @FXML
    private ScrollPane ticketScroll;

    // Add a new project to it's list on the sidebar
    public void addToScroll(Project createdProject) { // TODO: Add a search feature for these boxes
        VBox scrollVBox = Main.control.scrollVBox;
        ProjectBox newBox = new ProjectBox(createdProject);
        scrollVBox.getChildren().add(newBox);
        // Get the size after adding the ProjectBox
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

    // Initialize fxml components to access later on. Called on application open
    public void initialize() {
        if (searchTickets == null) {
            searchTickets = new TextField();
            searchTickets.setPromptText("Search Tickets");
        }
        if (searchArea == null) {
            searchArea = new TextField();
            searchArea.setPromptText("Search Projects");
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
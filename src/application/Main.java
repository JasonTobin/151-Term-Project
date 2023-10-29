package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    // Public static controller to allow other classes to access
    public static MainController control = new MainController();
    public static Connection conn;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("testing.fxml"));
        Parent root = loader.load();

        control = loader.getController();

        control.setHome();

        primaryStage.setTitle("Anteater V0.4");
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(900);
        // Add tickets and projects to scroll panes on UI
        for (Project p : ProjectList.getList()) {
            control.addToScroll(p);
        }
        for (Ticket t : TicketList.getList()) {
            control.addToTicketScroll(t);
        }
        primaryStage.show();
    }

    // Create tables if they dont exist, and verify that they do
    private static void checkTables() {
        System.out.println("Checking table");
        String sql = "CREATE TABLE IF NOT EXISTS tbl_projects (" +
                "   id integer PRIMARY KEY AUTOINCREMENT," +
                "   project_name text NOT NULL UNIQUE," +
                "   project_date text NOT NULL," +
                "   project_desc text" +
                ");";
        String secondql = "CREATE TABLE IF NOT EXISTS tbl_tickets (" +
                "   id integer PRIMARY KEY AUTOINCREMENT," +
                "   ticket_name text NOT NULL UNIQUE," +
                "   ticket_date text NOT NULL," +
                "   ticket_proj text NOT NULL," +
                "   ticket_comms text," +
                "   ticket_desc text" +
                ");";
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.executeUpdate(secondql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Load the projects, tickets, and comments into correct locations
    public static void loadData() {
        System.out.println("Loading data");
        ResultSet rs;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from tbl_projects");
            while (rs.next()) {
                Project createdProject = new Project(rs.getString(2), LocalDate.parse(rs.getString(3)),
                        rs.getString(4));
                ProjectList.AddProject(createdProject);
            }
            rs = stmt.executeQuery("select * from tbl_tickets");
            while (rs.next()) {
                Ticket createdTicket = new Ticket(rs.getString(2), LocalDate.parse(rs.getString(3)), rs.getString(4),
                        rs.getString(6), rs.getString(5));
                TicketList.AddTicket(createdTicket);
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String url = "jdbc:sqlite:projects.db";
        try {
            conn = DriverManager.getConnection(url);
            checkTables();
            // Initialize components on fxml file
            control.initialize();
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        launch(args); // Inherited method to show the screen
    }
}

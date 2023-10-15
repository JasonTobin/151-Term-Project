package application;

import java.sql.*;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

//TODO: For most of the on screen formatting TranslateX/Y is used, find a way to better do this

public class Main extends Application { // inherit application to allow usage in Main class
    public static final int DIMENSION = 500;
    static Connection conn;

    @Override
    public void start(Stage primaryStage) {
        try {
            /*
             * Below line of code creates the home screen, object with a VBOX layout
             * The default dimensions for this application are 500x500 therefor a constant
             * will be used
             */
            HomeScreen home = new HomeScreen(primaryStage, new VBox(), DIMENSION, DIMENSION);
            primaryStage.setMinWidth(DIMENSION);
            primaryStage.setMinHeight(DIMENSION);
            primaryStage.setTitle("Anteater V0.2");
            primaryStage.setScene(home);
            primaryStage.show();
            primaryStage.setOnCloseRequest(event -> {
                try {
                    conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void checkTables() {
        System.out.println("Checking table");
        String sql = "CREATE TABLE IF NOT EXISTS tbl_projects (" +
                "   id integer PRIMARY KEY AUTOINCREMENT," +
                "   project_name text NOT NULL UNIQUE," +
                "   project_date text NOT NULL," +
                "   project_desc text" +
                ");";
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadData() {
        System.out.println("Loading data");
        ResultSet rs;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from tbl_projects");
            while (rs.next()) {
                ProjectList.AddProject(new Project(rs.getString(2), LocalDate.parse(rs.getString(3)), rs.getString(4)));
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
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        launch(args); // Inherited method to show the screen
    }
}

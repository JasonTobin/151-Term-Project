import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeMap;

public class Project implements Comparable<Project> {
    private String projName;
    private LocalDate projDate; // Unsure if Date data structure works but for now keep
    // - Date picker required for this, find some library
    private String projDescription;
    private TreeMap<String, Ticket> TicketCollector;

    public boolean AddTicket(String n) {
        TicketCollector.put(n, new Ticket(n));
        return false;
    }

    public boolean AddTicket(String n, String d) {
        TicketCollector.put(n, new Ticket(n));
        return false;
    }

    public boolean DeleteTicket() {
        // Figure out how tickets are stored
        return false;
    }

    // Use this to store in some database in alphabetical order (learn SQL)
    @Override
    public int compareTo(Project e2) {
        return this.projName.compareTo(e2.projName);
    }

    public void setProjName(String n) {
        this.projName = n;
        return;
    }

    public void setProjDate(LocalDate d) {
        this.projDate = d;
        return;
    }

    public void setProjDesc(String n) {
        this.projDescription = n;
        return;
    }

    public String getProjName() {
        return this.projName;
    }

    public LocalDate getProjDate() {
        return this.projDate;
    }

    public String getProjDesc() {
        return this.projDescription;
    }

    public Project(String n, LocalDate p, String d) {
        this.projDate = p;
        this.projName = n;
        this.projDescription = d;
        this.TicketCollector = new TreeMap<String, Ticket>();
    }

    public Project(String n) { // Constructors for Project, must be a name for project
        this.projName = n;
        this.TicketCollector = new TreeMap<String, Ticket>();
    }

    public Project(String n, LocalDate p) {
        this.projName = n;
        this.projDate = p;
        this.TicketCollector = new TreeMap<String, Ticket>();
    }

    public Project(String n, String d) {
        this.projName = n;
        this.projDescription = d;
        this.TicketCollector = new TreeMap<String, Ticket>();
    }
}

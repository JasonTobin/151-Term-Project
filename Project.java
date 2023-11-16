package application;

import java.time.LocalDate;
import java.util.TreeMap;

public class Project implements Comparable<Project> {
    public static int idCount = 1;
    private String projName;
    private LocalDate projDate; // Local Date to store date user chose
    private String projDescription;
    private TreeMap<String, Ticket> TicketCollector;
    private int id;

    public boolean AddTicket(String n) { // Add a ticket to the project
        TicketCollector.put(n, new Ticket(n));
        return false;
    }

    public boolean AddTicket(String n, String d) {
        TicketCollector.put(n, new Ticket(n));
        return false;
    }
    
    

    public TreeMap<String, Ticket> getTickerCollector() {
        return this.TicketCollector;
    }

    // use this remove method when a project is deleted to 
    //delete all associated tickets connected to it.
    public boolean DeleteAllTickets() {
        TicketCollector.clear();
        return false;
    }

    // Use this to store in some database in alphabetical order (learn SQL)
    @Override
    public int compareTo(Project e2) {
        return this.projName.compareTo(e2.projName);
    }

    // Simple getters and setters and constructors

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

    public void setID(int i) {
        this.id = i;
    }

    public int getID() {
        return this.id;
    }

    public Project(String n, LocalDate p, String d, int i) {
        this.projDate = p;
        this.projName = n;
        this.projDescription = d;
        this.id = i;
        idCount++;
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

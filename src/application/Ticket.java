package application;

public class Ticket {
    private String bugName; // requied
    private String bugDesc; // opetional
    // private ArrayList<Comment> commentList;

    // TODO: Implement tickets and comments
    public boolean AddComment() {
        // Comments are not alphabetically sorted, therefore will be stored in an
        // ArrayList later on
        return false;
    }

    public boolean DeleteComment() {
        // same as add
        return false;
    }

    // Simple getters and setters

    public void setBugName(String n) {
        this.bugName = n;
        return;
    }

    public void setBugDesc(String n) {
        this.bugDesc = n;
        return;
    }

    public String getBugName() {
        return this.bugName;
    }

    public String getBugDesc() {
        return this.bugDesc;
    }

    public Ticket(String n) {
        this.bugName = n; // Name is required
    }

    public Ticket(String n, String d) {
        this.bugName = n;
        this.bugDesc = d; // optional description
    }
}

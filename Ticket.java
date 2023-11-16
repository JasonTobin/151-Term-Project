package application;

import java.time.LocalDate;
import java.util.ArrayList;

public class Ticket implements Comparable<Ticket> {
    private String bugName; // requied
    private String bugDesc; // optional
    private String projName;
    private LocalDate bugDate;
    private ArrayList<String> commentList = new ArrayList<>();

    public boolean AddComment(String s) {
        commentList.add(s);
        return false;
    }

    public String getComments() {
        String ret = "";
        for (String s : this.commentList) {
            ret += s;
            ret += ';';
        }
        return ret;
    }

    public ArrayList<String> getList() {
        return commentList;
    }

    @Override
    public int compareTo(Ticket e2) {
        return this.bugName.compareTo(e2.bugName);
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

    public void setBugDate(LocalDate d) {
        this.bugDate = d;
        return;
    }

    public LocalDate getBugDate() {
        return this.bugDate;
    }

    public void setTicketProjectName(String s) {
        this.projName = s;
    }

    public String getTicketProject() {
        return this.projName;
    }

    public Ticket(String n) {
        this.bugName = n; // Name is required
    }

    public Ticket(String n, String d) {
        this.bugName = n;
        this.bugDesc = d; // optional description
    }

    public Ticket(String n, LocalDate p, String d, String pn) {
        this.bugDate = p;
        this.bugName = n;
        this.bugDesc = d;
        this.projName = pn;
    }

    public Ticket(String n, LocalDate p, String d, String pn, String comments) {
        this.bugDate = p;
        this.bugName = n;
        this.bugDesc = d;
        this.projName = pn;
        if (comments != null) {
            String[] temp = comments.split(";", -1);
            for (String s : temp) {
                if (!s.isEmpty()) {
                    commentList.add(s);
                }
            }
        }
    }
}

package application;

import java.util.ArrayList;
import java.util.Comparator;

public class TicketList {
    // Exact same as projectList, but for tickets
    private static ArrayList<Ticket> ticketList = new ArrayList<Ticket>();

    private static Comparator<Ticket> com = new Comparator<Ticket>() {
        public int compare(Ticket e1, Ticket e2) {
            return e1.compareTo(e2);
        }
    };

    public static boolean containsName(String n) {
        for (Ticket e : ticketList) {
            if (e.getBugName().toLowerCase().equals(n.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static boolean AddTicket(Ticket p) {
        ticketList.add(p);
        ticketList.sort(com);
        return true;
    }

    public static ArrayList<Ticket> getList() {
        return ticketList;
    }
}

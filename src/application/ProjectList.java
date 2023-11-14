package application;

import java.util.ArrayList;
import java.util.Comparator;

public class ProjectList {
    // List of all created projects
    private static ArrayList<Project> projList = new ArrayList<Project>();

    public static Comparator<Project> com = new Comparator<Project>() {
        public int compare(Project e1, Project e2) {
            return e1.compareTo(e2);
        }
    };

    public static void delete(Project p) { // Called upon delete button call
        if (projList.contains(p)) {
            projList.remove(p);
            Main.control.updateScrollProj();
        }
    }

    public static boolean containsName(String n) {
        for (Project e : projList) {
            if (e.getProjName().toLowerCase().equals(n.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static boolean AddProject(Project p) {
        projList.add(p);
        projList.sort(com);
        return true;
    }

    public static ArrayList<Project> getList() {
        return projList;
    }
}

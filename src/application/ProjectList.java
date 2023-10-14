package application;

import java.util.ArrayList;
import java.util.Comparator;

public class ProjectList {
    private static ArrayList<Project> projList = new ArrayList<Project>();

    private static Comparator<Project> com = new Comparator<Project>() {
        public int compare(Project e1, Project e2) {
            return e1.compareTo(e2);
        }
    };

    public static boolean AddProject(Project p) {
        projList.add(p);
        projList.sort(com);
        return true;
    }

    public static ArrayList<Project> getList() {
        return projList;
    }
}

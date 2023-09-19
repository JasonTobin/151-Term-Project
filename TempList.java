import java.util.ArrayList;

public class TempList {
    private static ArrayList<Project> projList = new ArrayList<Project>();

    public static boolean AddProject(Project p) {
        projList.add(p);
        return true;
    }
}

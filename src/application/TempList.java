package application;

import java.util.ArrayList;
import java.util.Comparator;
/* current system for creating projects, stores tickets or "Projects" into an Arraylist 
 * called TempList, This list is used to be presented into other projects.
 * on the drop down menu when seeing past created projects or tickets in the APP.
 * 
 */
public class TempList {
    private static ArrayList<Project> projList = new ArrayList<Project>();

    private static Comparator<Project> com = new Comparator<Project>() {
        public int compare(Project e1, Project e2) {
            return e1.compareTo(e2);
        }
    };

    public static boolean AddProject(Project p) {
        
        for(int i = 0; i < projList.size(); i++){


        if(projList.get(i).getProjName().equals(p.getProjName())){
            return false;
        }
    }
            
        projList.add(p);
        projList.sort(com);
        return true;
        }


    public static ArrayList<Project> getList() {
        return projList;
    }
}
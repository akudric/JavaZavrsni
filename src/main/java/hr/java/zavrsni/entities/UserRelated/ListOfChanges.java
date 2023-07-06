package hr.java.zavrsni.entities.UserRelated;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ListOfChanges implements Serializable {

    private List<Changes> allChanges;

    public ListOfChanges(List<Changes> allChanges) {
        this.allChanges = allChanges;
    }
    public ListOfChanges(){
        allChanges = new ArrayList<>();
    }

    public List<Changes> getAllChanges() {
        return allChanges;
    }

    public void setAllChanges(List<Changes> allChanges) {
        this.allChanges = allChanges;
    }
}

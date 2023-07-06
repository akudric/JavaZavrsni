package hr.java.zavrsni.entities.UserRelated;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
public class Changes <X, Y> implements Serializable {
    private X oldValue;
    private Y newValue;
    String millis;
    public Changes(X tempOld, Y tempNew){
        tempNew = newValue;
        tempOld = oldValue;
    }

    public Changes() {

    }

    public X getOldValue() {
        return oldValue;
    }

    public void setOldValue(X oldValue) {
        this.oldValue = oldValue;
    }

    public Y getNewValue() {
        return newValue;
    }

    public void setNewValue(Y newValue) {
        this.newValue = newValue;
    }

    public String getMillis() {
        return millis;
    }

    public void setMillis(String millis) {
        this.millis = millis;
    }
}

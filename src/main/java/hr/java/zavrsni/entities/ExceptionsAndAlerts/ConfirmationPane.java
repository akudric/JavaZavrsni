package hr.java.zavrsni.entities.ExceptionsAndAlerts;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ConfirmationPane {
    JFrame frame = new JFrame();
    public boolean showConfirmationPane(String message){
        int answer = JOptionPane.showConfirmDialog(frame, message);
        if(answer == JOptionPane.YES_OPTION) return true;
        else return false;
    }
}

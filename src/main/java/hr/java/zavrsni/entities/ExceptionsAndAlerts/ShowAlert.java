package hr.java.zavrsni.entities.ExceptionsAndAlerts;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ShowAlert {

    public void displayAlert(String message, Alert.AlertType alertType){
        Alert a = new Alert(alertType);
        a.setContentText(message);
        a.show();
    }

    public boolean confirmationAlert(String message, Alert.AlertType alertType){
        Alert a = new Alert(alertType);
        a.setContentText(message);

        Optional<ButtonType> result = a.showAndWait();
        if(result.get() == ButtonType.OK){
            return true;
        }
        return false;
    }
}

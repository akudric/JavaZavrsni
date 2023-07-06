package hr.java.zavrsni.entities.ExceptionsAndAlerts;

import javafx.scene.control.Alert;
import org.apache.logging.log4j.Logger;

public class FailedLogin extends Exception{

    ShowAlert showAlert = new ShowAlert();

    public FailedLogin (String message, Logger logger){
        super(message);
        logger.error(message);
        showAlert.displayAlert(message, Alert.AlertType.ERROR);
    }
}

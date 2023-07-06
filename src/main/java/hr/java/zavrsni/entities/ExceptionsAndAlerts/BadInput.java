package hr.java.zavrsni.entities.ExceptionsAndAlerts;

import javafx.scene.control.Alert;
import org.apache.logging.log4j.Logger;

public class BadInput extends RuntimeException{

    ShowAlert showAlert = new ShowAlert();

    public BadInput (String message, Throwable cause){
        super(message,cause);

    }
    public BadInput (String message, Logger logger){
        super(message);
        showAlert.displayAlert(message, Alert.AlertType.ERROR);
        logger.error(message);
    }

}

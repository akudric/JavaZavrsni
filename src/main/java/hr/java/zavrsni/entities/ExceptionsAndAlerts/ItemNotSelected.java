package hr.java.zavrsni.entities.ExceptionsAndAlerts;

import javafx.scene.control.Alert;
import org.apache.logging.log4j.Logger;


public class ItemNotSelected extends RuntimeException{

    ShowAlert showAlert = new ShowAlert();

    public ItemNotSelected (String message, Logger logger){
        super(message);
        showAlert.displayAlert(message, Alert.AlertType.ERROR);
        logger.error(message);
    }


}

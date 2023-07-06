package hr.java.zavrsni.entities.Controllers;
import hr.java.zavrsni.entities.DataBase.BazaPodatakaDataSource;
import hr.java.zavrsni.entities.ExceptionsAndAlerts.FailedLogin;
import hr.java.zavrsni.entities.ExceptionsAndAlerts.ShowAlert;
import hr.java.zavrsni.entities.GameClassess.GameInfo;
import hr.java.zavrsni.entities.GameClassess.GameInfoBuilder;
import hr.java.zavrsni.entities.UserRelated.HashPass;
import hr.java.zavrsni.entities.UserRelated.User;
import hr.java.zavrsni.entities.fileInteractor.FileWriterAndReader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LoginController {

    public TextField directoryTextField;
    public TextField usernameTextField;
    public PasswordField passwordTextField;
    public Button loginButton;

    private static final Logger logger = LogManager.getLogger(LoginController.class);
    ShowAlert showAlert = new ShowAlert();



    public void initialize() throws IOException {

        GameInfo gs = new GameInfoBuilder().build();

        FileWriterAndReader <GameInfo> fileWriterAndReader = new FileWriterAndReader<>();

        fileWriterAndReader.writeToFile(gs);

        if (directoryTextField.getText() == null) directoryTextField.setText("C:/Users/Antonio/Desktop/JavaZavrsni/src/main/resources/LoadMe.txt");

        List<String> loadedData = loadData(directoryTextField.getText());

        usernameTextField.setText(loadedData.get(0));
        passwordTextField.setText(loadedData.get(1));
        loginButton.setOnAction(event -> {
            try {
                if(login(usernameTextField.getText(),passwordTextField.getText())){
                    showAlert.displayAlert("Uspjesno ste ulogirani", Alert.AlertType.CONFIRMATION);
                    openStartController();
                }
                else {
                    throw new FailedLogin("Netocni podaci",logger);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
    private void openStartController() throws IOException {
        URL url = new File("src/main/java/hr/java/zavrsni/entities/fxmlFiles/start.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();
        Stage stage = (Stage)loginButton.getScene().getWindow();
        Scene scene = new Scene(root,600,400);
        stage.setScene(scene);
        stage.show();
    }
    public boolean login(String username, String password) throws Exception {

        BazaPodatakaDataSource bazaPodatakaDataSource = new BazaPodatakaDataSource();

        HashPass <String, User> hashPass = new HashPass<>();

        if(bazaPodatakaDataSource.loginAttempt(username, hashPass.returnTheHash(password,null))){

            bazaPodatakaDataSource.close();

            return  true;
        }
        bazaPodatakaDataSource.close();
        return false;
    }


    List<String> loadData(String directory){


        List<String> returnValues = new ArrayList<>();

        try{
            BufferedReader br = new BufferedReader(new FileReader(directory));

            while (true){

                String temp = br.readLine();

                if(temp == null){
                    break;
                }
                returnValues.add(temp);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    return returnValues;
    }
}

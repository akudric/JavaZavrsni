package hr.java.zavrsni.entities.Controllers;
import hr.java.zavrsni.entities.DataBase.BazaPodatakaDataSource;
import hr.java.zavrsni.entities.ExceptionsAndAlerts.BadInput;
import hr.java.zavrsni.entities.ExceptionsAndAlerts.ConfirmationPane;
import hr.java.zavrsni.entities.ExceptionsAndAlerts.ShowAlert;
import hr.java.zavrsni.entities.GameClassess.GameInfo;
import hr.java.zavrsni.entities.fileInteractor.FileWriterAndReader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;

public class StartController {

    @FXML Button findBlackPlayerButton;
    @FXML Text usermode;
    @FXML TextField whiteplayerTextField;
    @FXML TextField blackplayerTextField;
    @FXML Button startGameButton;
    private boolean blackFlag = false;

    public void openPretrazivanjeIgraca() throws IOException {
        URL url = new File("src/main/java/hr/java/zavrsni/entities/fxmlFiles/pretrazivanjeigraca.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Pretrazivanje Igraca");
        stage.setScene(new Scene(root, 800, 800));
        stage.show();
    }
    public  void openPretrazivanjeIgri() throws IOException{
        URL url = new File("src/main/java/hr/java/zavrsni/entities/fxmlFiles/pretrazivanjeigri.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Pretrazivanje Igri");
        stage.setScene(new Scene(root, 800, 800));
        stage.show();
    }

    public void openChanges() throws IOException {
        URL url = new File("src/main/java/hr/java/zavrsni/entities/fxmlFiles/changes.fxml").toURI().toURL();
        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();

        Stage stage = new Stage();
        stage.setTitle("Changes");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }
    private static final Logger logger = LogManager.getLogger(StartController.class);

    FileWriterAndReader <GameInfo> fileWriterAndReader = new FileWriterAndReader<>();

    ShowAlert showAlert = new ShowAlert();



    public void initialize() throws IOException, ClassNotFoundException {

        whiteplayerTextField.setText(fileWriterAndReader.readFromFile().getWhitePlayerName());

        usermode.setText((Objects.equals(fileWriterAndReader.readFromFile().getIsWhiteAdmin(), "True")) ? "Admin mode":"User mode");


        findBlackPlayerButton.setOnAction(event -> {
            BazaPodatakaDataSource bazaPodatakaDataSource;
            try {
                bazaPodatakaDataSource = new BazaPodatakaDataSource();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                if(bazaPodatakaDataSource.findBlackPlayer(blackplayerTextField.getText())){
                    blackFlag = true;
                    showAlert.displayAlert("Uspjesno odabran crni igrac", Alert.AlertType.CONFIRMATION);
                }
                else {
                    throw new BadInput("Ne postoji taj igrac",logger);
                }
                } catch (SQLException | IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            bazaPodatakaDataSource.close();
        });
        startGameButton.setOnAction(event -> {
            try {
                if(blackFlag) startGame(whiteplayerTextField.getText(),blackplayerTextField.getText());
                else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Niste Odabrali protivnika");
                    a.show();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void startGame(String whitePlayerName, String blackPlayerName) throws IOException {

        URL url = new File("src/main/java/hr/java/zavrsni/entities/fxmlFiles/fxml.fxml").toURI().toURL();

        FXMLLoader loader = new FXMLLoader(url);
        Parent root = loader.load();


        Stage stage = new Stage();
        stage.setTitle(whitePlayerName+" vs "+blackPlayerName);
        stage.setScene(new Scene(root, 800, 800));
        stage.show();
    }
}

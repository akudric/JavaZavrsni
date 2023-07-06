package hr.java.zavrsni.entities.Controllers;


import hr.java.zavrsni.entities.DataBase.BazaPodatakaDataSource;
import hr.java.zavrsni.entities.ExceptionsAndAlerts.ShowAlert;
import hr.java.zavrsni.entities.UserRelated.Game;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.SQLException;

public class PretrazivanjeIgriController {

    @FXML TextField idIgreUnos;
    @FXML TextField idIgracUnos1;
    @FXML TextField idIgracUnos2;
    @FXML TextField pobjednikUnos;
    @FXML Button searchButton;
    @FXML TableColumn <Game,Integer> idIgreColumn;
    @FXML TableColumn <Game,Integer> igrac1IDColumn;
    @FXML TableColumn <Game,Integer> igrac2IDColumn;
    @FXML TableColumn <Game, String> winnerColumn;
    @FXML TableColumn <Game, Date> dateColumn;
    @FXML TableView <Game> igreTableView;

    private static final Logger logger = LogManager.getLogger(PretrazivanjeIgriController.class);

    public void initialize() throws Exception{
        BazaPodatakaDataSource baza = new BazaPodatakaDataSource();

        ShowAlert showAlert = new ShowAlert();

        searchButton.setOnAction(actionEvent ->{

            int igraID = (idIgreUnos.getText().trim().isEmpty()) ? -1 : Integer.parseInt(idIgreUnos.getText());
            int igrac1ID = (idIgracUnos1.getText().trim().isEmpty()) ? -1 : Integer.parseInt(idIgracUnos1.getText());
            int igrac2ID = (idIgracUnos2.getText().trim().isEmpty()) ? -1 : Integer.parseInt(idIgracUnos2.getText());
            String winner = (pobjednikUnos.getText().trim().isEmpty()) ? null : pobjednikUnos.getText();

            try{
                ObservableList<Game> games = baza.getGamesWithParam(igraID,igrac1ID,igrac2ID,winner);

                idIgreColumn.setCellValueFactory(new PropertyValueFactory<>("GameID"));
                igrac1IDColumn.setCellValueFactory(new PropertyValueFactory<>("Player1ID"));
                igrac2IDColumn.setCellValueFactory(new PropertyValueFactory<>("Player2ID"));
                winnerColumn.setCellValueFactory(new PropertyValueFactory<>("Winner"));
                dateColumn.setCellValueFactory(new PropertyValueFactory<>("DateOfGame"));

                igreTableView.setItems(games);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });
    }
}

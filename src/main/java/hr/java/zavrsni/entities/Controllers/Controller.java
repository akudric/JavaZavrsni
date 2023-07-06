package hr.java.zavrsni.entities.Controllers;

import hr.java.zavrsni.entities.GameClassess.GameInfo;
import hr.java.zavrsni.entities.GameClassess.Game;
import hr.java.zavrsni.entities.fileInteractor.FileWriterAndReader;
import javafx.fxml.FXML;
import javafx.scene.layout.*;

import java.io.IOException;


public class Controller {

    @FXML
    GridPane chessBoard;

    FileWriterAndReader <GameInfo> fileWriterAndReader = new FileWriterAndReader<>();

    public void initialize() throws Exception {

        //setTitle();

        Game game = new Game(chessBoard, "Chess.com",fileWriterAndReader.readFromFile());


    }
}
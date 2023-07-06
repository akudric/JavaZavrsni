package hr.java.zavrsni.entities.Controllers;

import hr.java.zavrsni.entities.UserRelated.Changes;
import hr.java.zavrsni.entities.UserRelated.ListOfChanges;
import hr.java.zavrsni.entities.UserRelated.User;
import hr.java.zavrsni.entities.fileInteractor.FileWriterAndReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.ObjectInputStream;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static java.time.format.DateTimeFormatter.*;

public class ChangesController {

    @FXML
    TableView <Changes> changesTableView;
    @FXML
    TableColumn<Changes, String> oldValueColumn = new TableColumn<>("OldValue");
    @FXML TableColumn <Changes, String> newValueColumn = new TableColumn<>("NewValue");
    @FXML TableColumn <Changes, String> timeOfChangeColumn = new TableColumn<>("TimeOfChange");

    public void initialize() throws Exception {


        FileWriterAndReader <Changes> readChanges = new FileWriterAndReader<>();

        ListOfChanges LOC = readChanges.readChanges();

        ObservableList <Changes> observableList = FXCollections.observableArrayList(LOC.getAllChanges());

        oldValueColumn.setCellValueFactory(new PropertyValueFactory<>("oldValue"));
        newValueColumn.setCellValueFactory(new PropertyValueFactory<>("newValue"));
        timeOfChangeColumn.setCellValueFactory(new PropertyValueFactory<>("millis"));


        changesTableView.setItems(observableList);


    }

}

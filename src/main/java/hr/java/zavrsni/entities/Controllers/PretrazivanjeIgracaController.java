package hr.java.zavrsni.entities.Controllers;
import hr.java.zavrsni.entities.DataBase.BazaPodatakaDataSource;
import hr.java.zavrsni.entities.ExceptionsAndAlerts.BadInput;
import hr.java.zavrsni.entities.ExceptionsAndAlerts.ItemNotSelected;
import hr.java.zavrsni.entities.ExceptionsAndAlerts.ShowAlert;
import hr.java.zavrsni.entities.GameClassess.GameInfo;
import hr.java.zavrsni.entities.UserRelated.Changes;
import hr.java.zavrsni.entities.UserRelated.ListOfChanges;
import hr.java.zavrsni.entities.UserRelated.User;
import hr.java.zavrsni.entities.fileInteractor.FileWriterAndReader;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class PretrazivanjeIgracaController {
    @FXML Text userModeText;
    @FXML TextField idigraca;
    @FXML TextField imeigraca;
    @FXML TextField passwordIgraca;
    @FXML Button trazi;
    @FXML Button editButton;
    @FXML Button deleteButton;
    @FXML Button saveChangesButton;
    @FXML Button createUserButton;
    @FXML CheckBox isAdminCheckBox;
    @FXML TableView <User> igracTableView;
    @FXML TableColumn <User, Integer> IDColumn = new TableColumn<>("ID");
    @FXML TableColumn <User, String> ImeColumn = new TableColumn<>("name");
    @FXML TableColumn <User, String> isAdminColumn = new TableColumn<>("isAdmin");

    private static final Logger logger = LogManager.getLogger(PretrazivanjeIgracaController.class);


    public void initialize () throws Exception {


        editButton.setDisable(true);
        deleteButton.setDisable(true);
        saveChangesButton.setDisable(true);

        final User[][] selectedUser = {new User[1]};

        FileWriterAndReader <GameInfo> fileWriterAndReader = new FileWriterAndReader<>();

        boolean isUserAdmin = Objects.equals(fileWriterAndReader.readFromFile().getIsWhiteAdmin(), "True");

        BazaPodatakaDataSource baza = new BazaPodatakaDataSource();

        ListOfChanges LOC = (fileWriterAndReader.readChanges().getAllChanges().isEmpty()) ? new ListOfChanges() : fileWriterAndReader.readChanges();


        ShowAlert showAlert = new ShowAlert();
        if(isUserAdmin){
            deleteButton.setDisable(false);
            editButton.setDisable(false);
            userModeText.setText("Admin mode");

            createUserButton.setOnAction(actionEvent ->{

                if (showAlert.confirmationAlert("Stovriti novog korisnika?",Alert.AlertType.CONFIRMATION)) {
                    try {
                        baza.createNewUser(imeigraca.getText(), isAdminCheckBox.isSelected(), passwordIgraca.getText());
                        showAlert.displayAlert("Usjpesno kreiran korisnik",Alert.AlertType.CONFIRMATION);
                        logger.info("Uspjesno kreiran korisnik");
                    } catch (BadInput | SQLException | NoSuchAlgorithmException e) {
                        throw new BadInput("Failed",e);
                    }
                }
            });

            deleteButton.setOnAction(actionEvent ->{
                if (!igracTableView.getSelectionModel().getSelectedItems().isEmpty()){
                    selectedUser[0][0] = igracTableView.getSelectionModel().getSelectedItem();
                    if(showAlert.confirmationAlert("Jeste sigurni da zelite pobrisati korisnika", Alert.AlertType.CONFIRMATION)) {
                        try {
                            baza.dropSelectedItem(selectedUser[0][0].getID(), "USERS");
                            showAlert.displayAlert("Uspjesno pobrisan korisnik", Alert.AlertType.CONFIRMATION);
                            logger.info("Uspjesno pobrisan korisnik");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                else{
                    throw new ItemNotSelected("Select an item!",logger);
                }
            });
            editButton.setOnAction(actionEvent ->{
                if (!igracTableView.getSelectionModel().getSelectedItems().isEmpty()){


                    saveChangesButton.setDisable(false);

                    selectedUser[0][0] = igracTableView.getSelectionModel().getSelectedItem();
                    idigraca.setText(String.valueOf(selectedUser[0][0].getID()));
                    imeigraca.setText(selectedUser[0][0].getName());
                    isAdminCheckBox.setSelected(Objects.equals(selectedUser[0][0].getIsAdmin(), "True"));

                    User userOld = new User(selectedUser[0][0].getID(),selectedUser[0][0].getName(),selectedUser[0][0].getIsAdmin());

                    saveChangesButton.setOnAction(actionEvent2 ->{
                        if (imeigraca.getText().isEmpty()){
                            throw new BadInput("Ime field cant be empty",logger);
                        }
                        else {

                            if (showAlert.confirmationAlert("Are you sure?",Alert.AlertType.CONFIRMATION)) {

                                //System.out.println("Tu sam u if-u");


                                String isAdmin = (isAdminCheckBox.isSelected()) ? "True" : "No";

                                try {

                                    //System.out.println("Tu sam u try");

                                    baza.updateSelectedItem(idigraca.getText(), "USERS", imeigraca.getText(), isAdmin);

                                    ObservableList<User> users = baza.getPlayersWithParam(Integer.parseInt(idigraca.getText()), imeigraca.getText(), isAdminCheckBox.isSelected());

                                    User userNew = users.get(0);

                                    Changes<User, User> changes = new Changes<>();

                                    changes.setOldValue(userOld);
                                    changes.setNewValue(userNew);
                                    Date date = Calendar.getInstance().getTime();
                                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
                                    changes.setMillis(dateFormat.format(date));
                                    LOC.getAllChanges().add(changes);
                                    FileWriterAndReader<ListOfChanges> writeChange = new FileWriterAndReader<>();
                                    writeChange.writeChanges(LOC);
                                    logger.info("Successfully changed user");
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    });
                }
                else{
                    throw new ItemNotSelected("Select an item!",logger);
                }
            });
        }
        trazi.setOnAction(actionEvent -> {

            boolean isAdmin = isAdminCheckBox.isSelected();
            int ID = (idigraca.getText().trim().isEmpty()) ? -1 : Integer.parseInt(idigraca.getText().trim());
            String name = (imeigraca.getText().trim().isEmpty()) ? null : imeigraca.getText();

            try {
                ObservableList<User> players = baza.getPlayersWithParam(ID,name,isAdmin);


                IDColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
                ImeColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
                isAdminColumn.setCellValueFactory(new PropertyValueFactory<>("isAdmin"));

                igracTableView.setItems(players);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

}

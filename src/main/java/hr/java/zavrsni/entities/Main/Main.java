package hr.java.zavrsni.entities.Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        URL url = new File("src/main/java/hr/java/zavrsni/entities/fxmlFiles/LoginScreen.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Start Menu");
        primaryStage.setScene(new Scene(root, 640, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void reset() {
//        Main.stage.close();
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view.fxml"));
//        Parent root = null;
//        try {
//            root = (Parent) fxmlLoader.load();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root));
//        stage.show();
//        Main.stage = stage;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
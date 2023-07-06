module hr.java.zavrsni {
    requires java.sql;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.apache.logging.log4j;

    opens hr.java.zavrsni.entities.GameClassess to javafx.base, javafx.fxml, javafx.graphics;
    opens hr.java.zavrsni.entities.Controllers to javafx.base, javafx.fxml, javafx.graphics;
    opens hr.java.zavrsni.entities.DataBase to javafx.base, javafx.fxml, javafx.graphics;
    opens hr.java.zavrsni.entities.UserRelated to javafx.base, javafx.fxml, javafx.graphics;
    opens hr.java.zavrsni.entities.Main to javafx.base, javafx.fxml, javafx.graphics;



}
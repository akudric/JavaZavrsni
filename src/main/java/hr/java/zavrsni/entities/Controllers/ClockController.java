package hr.java.zavrsni.entities.Controllers;

import hr.java.zavrsni.entities.Timers.PlayerClock;
import hr.java.zavrsni.entities.Timers.Timer;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class ClockController {

    private Timer timer;

    @FXML private Label time1;
    @FXML private Label time2;
    @FXML private Pane leftPane;
    @FXML private Pane rightPane;

private void setTimer(){

    this.timer = new Timer (new PlayerClock(300,"white",true),new PlayerClock(300,"black",false),time1,time2);

}

    public void initialize() {
        setTimer();
        time1.setText(timer.getLeftPlayer().getFormattedTime());
        time2.setText(timer.getRightPlayer().getFormattedTime());

        time1.setFocusTraversable(true);
        time1.requestFocus();
        time1.setOnKeyPressed(event -> {
            this.timer.switchPlayers();
        });
        startTimer();
    }
    private void startTimer() {
        Thread th = new Thread(this.timer);
        th.start();
    }
    public void switchSides() {
        this.timer.switchPlayers();
        //this.updateStyles();
    }
    public int getTime1(){
        String mainTime = this.time1.getText();
        String minTime = mainTime.substring(0,2);
        String secTime = mainTime.substring(3,5);
        return Integer.parseInt(minTime)*60 + Integer.parseInt(secTime);
    }
    public int getTime2(){

        String mainTime = this.time2.getText();
        String minTime = mainTime.substring(0,2);
        String secTime = mainTime.substring(3,5);
        return Integer.parseInt(minTime)*60 + Integer.parseInt(secTime);
    }
}

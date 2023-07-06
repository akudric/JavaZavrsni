package hr.java.zavrsni.entities.GameClassess;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameInfo implements Serializable {

    private long whitePlayerID;
    private long blackPlayerID;
    private String whitePlayerName;
    private String blackPlayerName;
    private String isWhiteAdmin;
    private String isBlackAdmin;
    private String winner;
    List<String> moveList = new ArrayList<>();

    public GameInfo() {

    }

    public String getIsWhiteAdmin() {
        return isWhiteAdmin;
    }

    public void setIsWhiteAdmin(String isWhiteAdmin) {
        this.isWhiteAdmin = isWhiteAdmin;
    }

    public void setIsBlackAdmin(String isBlackAdmin) {this.isBlackAdmin = isBlackAdmin;}

    public long getWhitePlayerID() {
        return whitePlayerID;
    }

    public void setWhitePlayerID(long whitePlayerID) {
        this.whitePlayerID = whitePlayerID;
    }

    public long getBlackPlayerID() {
        return blackPlayerID;
    }

    public void setBlackPlayerID(long blackPlayerID) {
        this.blackPlayerID = blackPlayerID;
    }

    public String getWhitePlayerName() {
        return whitePlayerName;
    }
    public void setWhitePlayerName(String whitePlayerName) {
        this.whitePlayerName = whitePlayerName;
    }
    public void setBlackPlayerName(String blackPlayerName) {
        this.blackPlayerName = blackPlayerName;
    }
    public String getBlackPlayerName() {
        return blackPlayerName;
    }
    public String getWinner() {
        return winner;
    }
    public void setWinner(String winner) {
        this.winner = winner;
    }
    public List<String> getMoveList() {
        return moveList;
    }
    public void addToMoveList(String move){
        this.moveList.add(move);
    }

    GameInfo(GameInfoBuilder builder){
        //this.whitePlayerID = builder.
    }
//zavrsi builder game info builder end of task 2 yayyy
}

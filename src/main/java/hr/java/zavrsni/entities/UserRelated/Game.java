package hr.java.zavrsni.entities.UserRelated;


public record Game(int getGameID, int getPlayer1ID, int getPlayer2ID, String getWinner, java.sql.Date getDateOfGame){}

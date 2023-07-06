package hr.java.zavrsni.entities.DataBase;

import hr.java.zavrsni.entities.ExceptionsAndAlerts.BadInput;
import hr.java.zavrsni.entities.GameClassess.GameInfo;
import hr.java.zavrsni.entities.UserRelated.HashPass;
import hr.java.zavrsni.entities.UserRelated.User;
import hr.java.zavrsni.entities.UserRelated.Game;

import hr.java.zavrsni.entities.fileInteractor.FileWriterAndReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.Objects;

public class BazaPodatakaDataSource {

    FileWriterAndReader <GameInfo> fileWriterAndReader = new FileWriterAndReader<>();

    private static final Logger logger = LogManager.getLogger(BazaPodatakaDataSource.class);


    private final Connection connection;
    public BazaPodatakaDataSource() throws Exception {
        try {
            connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");
        } catch (SQLException e) {
            throw new Exception(e);
        }
    }
    public void close() {
        try {
            connection.close();
        } catch (SQLException ignored) {}
    }
    public void sendGameToDB(GameInfo game) throws SQLException {
        String SQL = "INSERT INTO GAME(TIMEOFGAME, DAYOFGAME,WINNER, MOVELIST, PLAYER1ID, PLAYER2ID) VALUES(CURRENT_TIMESTAMP(9),CURRENT_DATE,?,?,?,?)";

        System.out.println(game.getWhitePlayerID()+" "+game.getBlackPlayerID());

        String movelist = String.join(",", game.getMoveList());

        PreparedStatement preparedStatement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setString(1,game.getWinner());
        preparedStatement.setString(2,movelist);
        preparedStatement.setString(3,String.valueOf(game.getWhitePlayerID()));
        preparedStatement.setString(4,String.valueOf(game.getBlackPlayerID()));

        preparedStatement.execute();

    }
    public boolean loginAttempt (String username, String password) throws SQLException, IOException, ClassNotFoundException {
        Statement statement = connection.createStatement();

        String SQL = "SELECT * FROM USERS WHERE USERNAME ='"+username+"' AND PASSWORD ='"+password+"'";

        ResultSet rs = statement.executeQuery(SQL);


        if(rs.next()) {

            GameInfo gi = fileWriterAndReader.readFromFile();

            gi.setWhitePlayerName(rs.getString("USERNAME"));
            gi.setWhitePlayerID(rs.getInt("ID"));
            gi.setIsWhiteAdmin(rs.getString("ISADMIN"));

            fileWriterAndReader.writeToFile(gi);
            return true;
        }
        return false;

    }
    public boolean findBlackPlayer(String username) throws SQLException, IOException, ClassNotFoundException {
        Statement statement = connection.createStatement();

        String SQL = "SELECT * FROM USERS WHERE USERNAME ='"+username+"'";

        ResultSet rs = statement.executeQuery(SQL);

        GameInfo gi = fileWriterAndReader.readFromFile();


        if(rs.next()) {

            gi.setBlackPlayerName(rs.getString("USERNAME"));
            gi.setBlackPlayerID(rs.getInt("ID"));
            gi.setIsBlackAdmin(rs.getString("ISADMIN"));

            fileWriterAndReader.writeToFile(gi);
            return true;
        }
        return false;
    }
    public void updateSelectedItem(String ID, String table,String username, String isAdmin) throws SQLException {
        Statement statement = connection.createStatement();

        String SQL = "";

        if(table.isEmpty()||username.isEmpty()){throw new BadInput("Field cant be empty",logger);}

        if(Objects.equals(table, "USERS")){
            SQL = "UPDATE USERS SET USERNAME = '"+username+"', ISADMIN = '"+isAdmin+"' WHERE ID='"+ID+"'";
        }
        statement.executeUpdate(SQL);
    }
    public void dropSelectedItem(int ID, String table) throws SQLException {

        Statement statement = connection.createStatement();

        String SQL = "";

        if(Objects.equals(table, "USERS")){
            SQL = "DELETE FROM USERS WHERE ID ='"+ID+"'";
        }
        statement.executeUpdate(SQL);
    }

    public void createNewUser(String name, boolean isAdmin,String password) throws SQLException, NoSuchAlgorithmException {
        Statement statement = connection.createStatement();

        String stIsAdmin = (isAdmin)? "True" : "No";

        HashPass <String, String> hashPass = new HashPass<>();

        String hashedPass = hashPass.returnTheHash(password, null);

        if(name.isEmpty()||password.isEmpty()){throw new BadInput("Username cant be null",logger);}

        String SQL = "INSERT INTO USERS (USERNAME,ISADMIN,PASSWORD) VALUES ('"+name+"','"+stIsAdmin+"','"+hashedPass+"')";
        try{statement.executeUpdate(SQL);}
        catch (Exception e){System.out.println(e);};
    }

    public ObservableList<Game> getGamesWithParam(int id, int player1ID,int player2ID, String winner) throws SQLException {
        Statement statement = connection.createStatement();

        String SQL;

        ObservableList <Game> games = FXCollections.observableArrayList();

        String stringID = (id==-1)?"TRUE" : "IDGAME='"+id+"'";
        String player1IDString = (player1ID == -1) ? "TRUE" : "PLAYER1ID ='"+player1ID+"'";
        String player2IDString = (player2ID == -1) ? "TRUE" : "PLAYER2ID ='"+player2ID+"'";
        winner = (winner == null) ? "TRUE" : "WINNER = '"+winner+"'";

        SQL = "SELECT * FROM GAME WHERE "+stringID+" AND "+player1IDString+" AND "+player2IDString+" AND "+ winner;

        ResultSet rs = statement.executeQuery(SQL);

        while(rs.next()){
            Game game = new Game(rs.getInt("IDGAME"),rs.getInt("PLAYER1ID"), rs.getInt("PLAYER2ID"),rs.getString("WINNER"),rs.getDate("DAYOFGAME"));

            games.add(game);
        }
        return games;
    }
    public ObservableList<User> getPlayersWithParam(int id, String name, boolean isAdmin) throws SQLException, IOException {

        Statement statement = connection.createStatement();

        String SQL;

        ObservableList <User> players2 = FXCollections.observableArrayList();

        String stringID = (id == -1)? "TRUE" : "ID='"+id+"'";

        name = (name == null)?"TRUE" : "USERNAME = '"+name+"'";

        String tempIsAdmin = (isAdmin) ? "'True'" : "'No'";

        SQL = "SELECT * FROM USERS WHERE "+stringID+ " AND "+name+" AND ISADMIN = "+tempIsAdmin;

        ResultSet rs = statement.executeQuery(SQL);

        while (rs.next()){
            User user = new User(rs.getInt("ID"),rs.getString("USERNAME"),rs.getString("ISADMIN"));

            players2.add(user);

        }

        return players2;
    }

}

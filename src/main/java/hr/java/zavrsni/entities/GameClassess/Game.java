package hr.java.zavrsni.entities.GameClassess;

import hr.java.zavrsni.entities.Controllers.ClockController;
import hr.java.zavrsni.entities.Controllers.StartController;
import hr.java.zavrsni.entities.DataBase.BazaPodatakaDataSource;
import javafx.event.EventTarget;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.Objects;

public class Game {

    private static Piece currentPiece;
    public static String currentPlayer;
    public static ChessBoard cb;
    private boolean game;
    private final GameInfo gs;


    BazaPodatakaDataSource bazaPodatakaDataSource = new BazaPodatakaDataSource();

    public Game(GridPane chessBoard, String theme, GameInfo gs) throws Exception {

        URL urlClock = new File("src/main/java/hr/java/zavrsni/entities/fxmlFiles/clock.fxml").toURI().toURL();
        FXMLLoader loader2 = new FXMLLoader(urlClock);
        Parent root2 = loader2.load();
        ClockController clockController = loader2.getController();
        Stage stage2 = new Stage();
        stage2.setTitle("Clock");
        stage2.setScene(new Scene(root2));
        stage2.show();

        cb = new ChessBoard(chessBoard, theme);
        currentPiece = null;
        currentPlayer = "white";
        this.game = true;
        this.gs=gs;
        addEventHandlers(cb.chessBoard,clockController);
    }
    private void addEventHandlers(GridPane chessBoard,ClockController clockController){

        chessBoard.setOnMouseClicked(event -> {
            EventTarget target = event.getTarget();

            System.out.println(clockController.getTime1());
            System.out.println(clockController.getTime2());

            if(clockController.getTime1()==0){
                gs.setWinner("black");
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setContentText("Pobijedio je "+ gs.getBlackPlayerName());
                a.show();
                try {
                    bazaPodatakaDataSource.sendGameToDB(gs);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                this.game = false;
            }
            if(clockController.getTime2()==0){
                gs.setWinner("white");
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setContentText("Pobijedio je "+ gs.getWhitePlayerName());
                a.show();
                try {
                    bazaPodatakaDataSource.sendGameToDB(gs);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                this.game = false;
            }
            if(target.toString().equals("Square")){
                Square square = (Square) target;
                if(square.occupied){
                    Piece newPiece = (Piece) square.getChildren().get(0);
                    // Selecting a new piece
                    if(currentPiece == null){
                        currentPiece = newPiece;
//                            currentPiece.getAllPossibleMoves();
                        if(!currentPiece.getColor().equals(currentPlayer)){
                            currentPiece = null;
                            return;
                        }
                        selectPiece(game);
                    }
                    // Selecting other piece of same color || Killing a piece
                    else{
                        if(currentPiece.color.equals(newPiece.color)){
                            deselectPiece(false);
                            currentPiece = newPiece;
//                                currentPiece.getAllPossibleMoves();
                            selectPiece(game);
                        }
                        else{
                            killPiece(square,clockController);
                        }
                    }

                }
                // Dropping a piece on blank square
                else{
                    //tuuu
                    dropPiece(square,clockController);
                }
            }
            // Clicked on piece
            else{
                Piece newPiece = (Piece) target;
                Square square = (Square) newPiece.getParent();
                // Selecting a new piece
                if(currentPiece == null){
                    currentPiece = newPiece;
                    if(!currentPiece.getColor().equals(currentPlayer)){
                        currentPiece = null;
                        return;
                    }
                    selectPiece(game);
                }
                // Selecting other piece of same color || Killing a piece
                else{
                    if(currentPiece.color.equals(newPiece.color)){
                        deselectPiece(false);
                        currentPiece = newPiece;
                        selectPiece(game);
                    }
                    else{
                        killPiece(square,clockController);
                    }
                }
            }
        });
    }

    private void selectPiece(boolean game){
        if(!game){
            currentPiece = null;
            return;
        }

        DropShadow borderGlow = new DropShadow();
        borderGlow.setColor(Color.BLACK);
        borderGlow.setOffsetX(0f);
        borderGlow.setOffsetY(0f);
        currentPiece.setEffect(borderGlow);
        currentPiece.getAllPossibleMoves();
        currentPiece.showAllPossibleMoves(true);
    }

    private void deselectPiece(boolean changePlayer){
        currentPiece.setEffect(null);
        currentPiece.showAllPossibleMoves(false);
        currentPiece = null;
        if(changePlayer) currentPlayer = currentPlayer.equals("white") ? "black" : "white";
    }

    private void dropPiece(Square square,ClockController clockController){
        if(!currentPiece.possibleMoves.contains(square.name)) return;

        Square initialSquare = (Square) currentPiece.getParent();
        square.getChildren().add(currentPiece);
        square.occupied = true;
        initialSquare.getChildren().removeAll();
        initialSquare.occupied = false;
        currentPiece.posX = square.x;
        currentPiece.posY = square.y;
        writeMove(square.x,square.y,currentPiece,false);
        deselectPiece(true);
        clockController.switchSides();
    }

    private void killPiece(Square square,ClockController clockController){
        if(!currentPiece.possibleMoves.contains(square.name)) return;

        Piece killedPiece = (Piece) square.getChildren().get(0);
        if(killedPiece.type.equals("King")) {

            writeMove(square.x,square.y,currentPiece,true);

            gs.setWinner(currentPlayer);
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);



            if(Objects.equals(currentPlayer, "white")){
                a.setContentText("Pobijedio je "+ gs.getWhitePlayerName());
            }
            else {
                a.setContentText("Pobijedio je "+ gs.getBlackPlayerName());
            }
            a.show();
            try {
                bazaPodatakaDataSource.sendGameToDB(gs);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            this.game = false;
        }


        Square initialSquare = (Square) currentPiece.getParent();
        square.getChildren().remove(0);
        square.getChildren().add(currentPiece);
        square.occupied = true;
        initialSquare.getChildren().removeAll();
        initialSquare.occupied = false;
        currentPiece.posX = square.x;
        currentPiece.posY = square.y;
        writeMove(square.x,square.y,currentPiece,true);
        deselectPiece(true);
        clockController.switchSides();
    }

    private void writeMove(int x, int y, Piece piece,boolean kill){
        char file = switch (x) {
            case 0 -> 'A';
            case 1 -> 'B';
            case 2 -> 'C';
            case 3 -> 'D';
            case 4 -> 'E';
            case 5 -> 'F';
            case 6 -> 'G';
            case 7 -> 'H';
            default -> '?';
        };
        if(kill){
            gs.addToMoveList(piece.getColor()+" "+piece.getType()+" X "+file+" "+(y+1));
            return;
        }
        gs.addToMoveList(piece.getColor()+" "+piece.getType()+" "+file+" "+(y+1));
    }
}

package hr.java.zavrsni.entities.GameClassess;

import javafx.event.EventHandler;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

sealed abstract class Piece extends ImageView implements Moves permits Bishop, King, Knight, Pawn, Queen, Rook {
    String type;
    String color;
    int posX, posY;
    ArrayList<String> possibleMoves;

    public Piece(String color, int posX, int posY){
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        addEventHandler();
    }

    public String getColor(){
        return this.color;
    }

    public void setPiece(Image image){
        this.setImage(image);
    }

    public void setImage() {
        URL url;
        try {
            url = new File("src/main/java/hr/java/zavrsni/entities/pieces/" + this.color + "" + this.type + ".png").toURI().toURL();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.setPiece(new Image(String.valueOf(url)));

    }

    private void addEventHandler(){
        this.setOnMouseClicked(event -> getAllPossibleMoves());
    }

    public void getAllPossibleMoves() {}

    public void showAllPossibleMoves(boolean val){
        if(val){
            Glow glow = new Glow();
            glow.setLevel(0.8);
            for(String move : possibleMoves){
                Square square = getSquareByName(move);
                square.setEffect(glow);

                Piece piece = getPieceByName(move);
                if(piece == null) continue;
                if(piece.type.equals("King")){
                    square.setBorder(new Border(new BorderStroke(Color.DARKRED,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.5))));
                }
                else{
                    square.setBorder(new Border(new BorderStroke(Color.BLACK,
                            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1.2))));
                }
            }
        }
        else{
            for(String move : possibleMoves){
                Square square = getSquareByName(move);
                square.setEffect(null);
                square.setBorder(new Border(new BorderStroke(Color.BLACK,
                        BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
            }
        }
    }

    public Square getSquareByName(String name){
        for(Square square : Game.cb.squares){
            if(square.name.equals(name)){
                return square;
            }
        }

        return null;
    }

    public Piece getPieceByName(String name){
        for(Square square : Game.cb.squares){
            if(square.getChildren().size() == 0) continue;

            if(square.name.equals(name))
                return (Piece) square.getChildren().get(0);

        }
        return null;
    }

    @Override
    public String toString() {
        return this.color + " " + this.type;
    }

    public String getType() {
        return type;
    }
}
package hr.java.zavrsni.entities.GameClassess;

sealed interface Moves permits Piece {
    public void getAllPossibleMoves();
    public void setImage();

}

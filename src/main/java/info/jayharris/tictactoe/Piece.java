package info.jayharris.tictactoe;

public enum Piece {
    X, O;

    public Piece opposite() {
        return this == Piece.X ? Piece.O : Piece.X;
    }
}

package info.jayharris.tictactoe;

import java.util.Optional;

public class Square {

    private Optional<Piece> piece;

    Square() {
        piece = Optional.empty();
    }

    public Optional<Piece> getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = Optional.of(piece);
    }

    public static Square create(int i) {
        return new Square();
    }
}

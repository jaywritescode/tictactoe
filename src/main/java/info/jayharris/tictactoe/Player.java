package info.jayharris.tictactoe;

import org.apache.commons.lang3.tuple.Pair;

public abstract class Player {

    public final Piece piece;

    public Player(Piece piece) {
        this.piece = piece;
    }

    public abstract Pair<Integer, Integer> getMove();
}

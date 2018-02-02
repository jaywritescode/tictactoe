package info.jayharris.tictactoe.player;

import info.jayharris.tictactoe.Piece;
import info.jayharris.tictactoe.Tictactoe;
import org.apache.commons.lang3.tuple.Pair;

public abstract class Player {

    public final Piece piece;

    public Player(Piece piece) {
        this.piece = piece;
    }

    public abstract Pair<Integer, Integer> getMove(Tictactoe game);

    public void begin(Tictactoe game) { }

    public void end(Tictactoe game) { }

    public void fail(Tictactoe game, Exception e) { }
}

package info.jayharris.tictactoe.player;

import info.jayharris.tictactoe.Move;
import info.jayharris.tictactoe.Piece;
import info.jayharris.tictactoe.Tictactoe;

public abstract class Player {

    public final Piece piece;

    protected Player(Piece piece) {
        this.piece = piece;
    }

    public abstract Move getMove(Tictactoe game);

    public void begin(Tictactoe game) { }

    public void end(Tictactoe game) { }

    public void fail(Tictactoe game, Exception e) { }
}

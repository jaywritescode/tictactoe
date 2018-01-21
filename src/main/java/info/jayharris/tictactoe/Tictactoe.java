package info.jayharris.tictactoe;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;

public class Tictactoe {

    Player current, x, o;
    final Board board = new Board();

    public Tictactoe(Player x, Player o) {
        current = this.x = x;
        this.o = o;
    }

    public Outcome play() {
        Outcome winner;
        while ((winner = nextPly()) == null) {
            current = (current == x ? o : x);
        }

        return winner;
    }

    /**
     * Plays the next ply of the game.
     *
     * @return the outcome if the game is over, otherwise null
     */
    private Outcome nextPly() {
        while(true) {
            try {
                board.setPiece(current.getMove(), current.piece);
            }
            catch (IllegalArgumentException e) { }
        }
    }

    private Outcome gameOver() {
       return null;
    }


}

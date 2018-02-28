package info.jayharris.tictactoe;

import org.apache.commons.lang3.tuple.Pair;

/**
 * References a square on the board that a player can play at.
 */
public class Move {

    private final int index;

    /**
     * Constructor.
     *
     * @param index the index into the underlying array
     */
    public Move(int index) {
        this.index = index;
    }

    /**
     * Constructor.
     *
     * @param game the game
     * @param coords a (row, column) pair
     */
    public Move(Tictactoe game, Pair<Integer, Integer> coords) {
        this.index = coords.getLeft() * game.getSize() + coords.getRight();
    }

    public int getIndex() {
        return index;
    }
}

package info.jayharris.tictactoe;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Objects;

/**
 * References a square on the board that a player can play at.
 */
public class Move {

    private final int index;

    private Move(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return index == move.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    @Override
    public String toString() {
        return "Move{" + "index=" + index + '}';
    }

    /**
     * Create a new Move.
     *
     * @param index the index we're playing at. The upper-left square is index 0, and
     *              indices are counted going right and then down.
     * @return a Move at the given index
     */
    public static Move at(int index) {
        return new Move(index);
    }

    public static Move at(Pair<Integer, Integer> coords, Tictactoe game) {
        return new Move(coords.getLeft() * game.getSize() + coords.getRight());
    }
}

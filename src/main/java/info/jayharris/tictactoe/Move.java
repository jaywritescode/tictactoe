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

    private Move(Square grid, Pair<Integer, Integer> coords) {
        this.index = coords.getLeft() * grid.getSize() + coords.getRight();
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
        final StringBuffer sb = new StringBuffer("Move{");
        sb.append("index=").append(index);
        sb.append('}');
        return sb.toString();
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

    /**
     * Create a new Move.
     *
     * @param grid the game or board
     * @param coords the move coordinates as {@code Pair.of(row, column)}. The leftmost
     *               column and the topmost row are zero.
     * @return a Move at the given coordinates
     */
    public static Move at(Square grid, Pair<Integer, Integer> coords) {
        return new Move(grid, coords);
    }
}

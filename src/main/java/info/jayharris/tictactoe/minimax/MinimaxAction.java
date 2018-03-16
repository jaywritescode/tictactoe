package info.jayharris.tictactoe.minimax;

import info.jayharris.minimax.Action;
import info.jayharris.tictactoe.Board;
import info.jayharris.tictactoe.Move;

import java.util.Objects;

public class MinimaxAction implements Action<MinimaxState> {

    Move move;

    MinimaxAction(int squareRef) {
        this.move = Move.at(squareRef);
    }

    @Override
    public MinimaxState apply(MinimaxState state) {
        Board board = state.getBoardCopy();
        board.setPiece(move, state.getToMove());

        return new MinimaxState(board, state.getToMove().opposite());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MinimaxAction that = (MinimaxAction) o;
        return Objects.equals(move, that.move);
    }

    @Override
    public int hashCode() {
        return Objects.hash(move);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MinimaxAction{");
        sb.append("move=").append(move);
        sb.append('}');
        return sb.toString();
    }

    public static MinimaxAction from(int squareRef) {
        return new MinimaxAction(squareRef);
    }
}

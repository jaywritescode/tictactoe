package info.jayharris.tictactoe.minimax;

import info.jayharris.minimax.Action;
import info.jayharris.tictactoe.Board;
import info.jayharris.tictactoe.Move;

public class MinimaxAction implements Action<MinimaxState, MinimaxAction> {

    private Move move;

    private MinimaxAction(Move move) {
        this.move = move;
    }

    @Override
    public MinimaxState apply(MinimaxState initialState) {
        Board copy = Board.copyFrom(initialState.getBoard());
        copy.setPiece(move, initialState.getToMove());
        return new MinimaxState(copy, initialState.getToMove().opposite());
    }

    public Move getMove() {
        return move;
    }

    static MinimaxAction create(Move move) {
        return new MinimaxAction(move);
    }
}

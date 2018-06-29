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
    public MinimaxState apply(MinimaxState minimaxState) {
        Board copy = Board.copyFrom(minimaxState.getBoard());
        copy.setPiece(move, minimaxState.getNextPiece());
        return minimaxState.successor(copy);
    }

    public Move getMove() {
        return move;
    }

    static MinimaxAction from(Move move) {
        return new MinimaxAction(move);
    }
}

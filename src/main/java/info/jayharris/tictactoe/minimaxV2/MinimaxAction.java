package info.jayharris.tictactoe.minimaxV2;

import info.jayharris.minimax.Action;
import info.jayharris.tictactoe.Move;

public class MinimaxAction implements Action<MinimaxState, MinimaxAction> {

    Move move;

    private MinimaxAction(Move move) {
        this.move = move;
    }

    @Override
    public MinimaxState apply(MinimaxState minimaxState) {
        return null;
    }

    public static MinimaxAction from(Move move) {
        return new MinimaxAction(move);
    }
}

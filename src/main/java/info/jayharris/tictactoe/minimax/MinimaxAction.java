package info.jayharris.tictactoe.minimax;

import info.jayharris.minimax.Action;
import info.jayharris.tictactoe.Move;

public class MinimaxAction implements Action<MinimaxState, MinimaxAction> {

    Move move;

    MinimaxAction(int squareRef) {
        this.move = Move.at(squareRef);
    }

    @Override
    public MinimaxState apply(MinimaxState state) {
        return new MinimaxState(state, move);
    }

    public Move getMove() {
        return move;
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

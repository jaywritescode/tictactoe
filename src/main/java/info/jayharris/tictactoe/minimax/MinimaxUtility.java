package info.jayharris.tictactoe.minimax;

import info.jayharris.minimax.Utility;
import info.jayharris.tictactoe.Outcome;
import info.jayharris.tictactoe.Piece;

public class MinimaxUtility implements Utility<Long> {

    MinimaxState state;
    Piece piece;

    /**
     * Constructor.
     *
     * @param state the state we're calculating the utility of
     * @param piece the piece whose perspective the utility is from
     */
    MinimaxUtility(MinimaxState state, Piece piece) {
        this.state = state;
        this.piece = piece;
    }

    @Override
    public Long get() {
        if (!state.terminalTest()) {
            return null;
        }

        Outcome outcome = state.getBoard().getOutcome().get();

        if (outcome == Outcome.tie()) {
            return 0L;
        }

        return outcome.winner() == piece ? Long.MAX_VALUE : Long.MIN_VALUE;
    }
}

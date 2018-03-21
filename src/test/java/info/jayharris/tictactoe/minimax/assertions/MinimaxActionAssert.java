package info.jayharris.tictactoe.minimax.assertions;

import info.jayharris.tictactoe.Move;
import info.jayharris.tictactoe.minimax.MinimaxAction;
import org.assertj.core.api.AbstractAssert;

public class MinimaxActionAssert extends AbstractAssert<MinimaxActionAssert, MinimaxAction> {

    public MinimaxActionAssert(MinimaxAction actual) {
        super(actual, MinimaxActionAssert.class);
    }

    public MinimaxActionAssert hasMove(Move move) {
        isNotNull();

        if (!actual.getMove().equals(move)) {
            failWithMessage("Expected move to be <%s> but was <%s>", move.toString(), actual.getMove().toString());
        }

        return this;
    }
}

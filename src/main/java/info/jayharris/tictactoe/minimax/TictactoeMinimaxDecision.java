package info.jayharris.tictactoe.minimax;

import info.jayharris.minimax.MinimaxDecision;

public class TictactoeMinimaxDecision extends MinimaxDecision<MinimaxState, Long, MinimaxUtility> {

    private TictactoeMinimaxDecision(MinimaxState root) {
        super(root);
    }

    public static TictactoeMinimaxDecision doCreate(MinimaxState root) {
        return new TictactoeMinimaxDecision(root);
    }
}

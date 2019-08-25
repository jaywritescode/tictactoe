package info.jayharris.tictactoe.minimax;

import info.jayharris.minimax.EvaluationFunction;
import info.jayharris.tictactoe.Outcome;
import info.jayharris.tictactoe.TictactoeUtils;
import info.jayharris.tictactoe.player.Player;

public class TictactoeEvaluationFunction implements EvaluationFunction<MinimaxState> {

    private final Player player;

    final static long WIN = 1, TIE = 0, LOSS = -1;

    private TictactoeEvaluationFunction(Player player) {
        this.player = player;
    }

    @Override
    public double apply(MinimaxState state) {
        Outcome outcome = TictactoeUtils.getOutcome(state.getBoard())
                .orElseThrow(() -> new IllegalStateException("Expected a terminal node"));

        if (outcome.isTie()) {
            return TIE;
        }
        return outcome.winner().equals(player.getPiece()) ? WIN : LOSS;
    }

    public static TictactoeEvaluationFunction create(Player player) {
        return new TictactoeEvaluationFunction(player);
    }
}

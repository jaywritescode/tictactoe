package info.jayharris.tictactoe.player;

import info.jayharris.minimax.MaxNode;
import info.jayharris.minimax.search.SimpleMinimaxDecision;
import info.jayharris.tictactoe.Board;
import info.jayharris.tictactoe.Move;
import info.jayharris.tictactoe.Piece;
import info.jayharris.tictactoe.Tictactoe;
import info.jayharris.tictactoe.minimax.MinimaxAction;
import info.jayharris.tictactoe.minimax.MinimaxState;
import info.jayharris.tictactoe.minimax.TictactoeEvaluationFunction;

public class MinimaxPlayer extends Player {

    private final TictactoeEvaluationFunction eval;

    public MinimaxPlayer(Piece piece) {
        super(piece);
        this.eval = new TictactoeEvaluationFunction(this);
    }

    @Override
    public Move getMove(Tictactoe game) {
        MaxNode<MinimaxState, MinimaxAction> root = new MaxNode<>(
                new MinimaxState(Board.copyFrom(game), getPiece()),null, 0, eval);
        SimpleMinimaxDecision<MinimaxState, MinimaxAction> decision = new SimpleMinimaxDecision<>(root);

        return decision.perform().getMove();
    }
}

package info.jayharris.tictactoe.player;

import info.jayharris.minimax.DecisionTree;
import info.jayharris.tictactoe.Move;
import info.jayharris.tictactoe.Piece;
import info.jayharris.tictactoe.Tictactoe;
import info.jayharris.tictactoe.minimax.MinimaxAction;
import info.jayharris.tictactoe.minimax.MinimaxState;

public class MinimaxPlayer extends Player {

    DecisionTree<MinimaxState, MinimaxAction> decisionTree;

    public MinimaxPlayer(Piece piece) {
        super(piece);
    }

    @Override
    public Move getMove(Tictactoe game) {
        if (decisionTree != null) {
            decisionTree = new DecisionTree<>(MinimaxState.root(game, this), decisionTree.getTranspositionTable());
        }
        else {
            decisionTree = new DecisionTree<>(MinimaxState.root(game, this));
        }

        return decisionTree.perform().getMove();
    }
}

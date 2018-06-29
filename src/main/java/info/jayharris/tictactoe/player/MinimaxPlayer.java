package info.jayharris.tictactoe.player;

import info.jayharris.minimax.DecisionTree;
import info.jayharris.tictactoe.Move;
import info.jayharris.tictactoe.Piece;
import info.jayharris.tictactoe.Tictactoe;
import info.jayharris.tictactoe.minimax.MinimaxAction;
import info.jayharris.tictactoe.minimax.MinimaxState;

public class MinimaxPlayer extends Player {

    public MinimaxPlayer(Piece piece) {
        super(piece);
    }

    @Override
    public Move getMove(Tictactoe game) {
        DecisionTree<MinimaxState, MinimaxAction> decisionTree = new DecisionTree<>(MinimaxState.root(game, this));

        return decisionTree.perform().getMove();
    }
}

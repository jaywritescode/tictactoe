package info.jayharris.tictactoe.player;

import info.jayharris.minimax.DecisionTree;
import info.jayharris.tictactoe.Move;
import info.jayharris.tictactoe.Piece;
import info.jayharris.tictactoe.Tictactoe;
import info.jayharris.tictactoe.minimax.MinimaxAction;
import info.jayharris.tictactoe.minimax.MinimaxState;
import info.jayharris.tictactoe.minimax.TictactoeCutoffTest;
import info.jayharris.tictactoe.minimax.TictactoeTranspositions;

public class MinimaxPlayer extends Player {

    DecisionTree<MinimaxState, MinimaxAction> decisionTree;

    public MinimaxPlayer(Piece piece) {
        super(piece);
    }

    @Override
    public Move getMove(Tictactoe game) {
        decisionTree = new DecisionTree<>(
                MinimaxState.root(game, this),
                TictactoeTranspositions.create(),
                TictactoeCutoffTest.create());

        return decisionTree.perform().getMove();
    }
}

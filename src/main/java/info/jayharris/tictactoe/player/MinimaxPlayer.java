package info.jayharris.tictactoe.player;

import info.jayharris.minimax.MinimaxDecision;
import info.jayharris.tictactoe.Move;
import info.jayharris.tictactoe.Piece;
import info.jayharris.tictactoe.Tictactoe;
import info.jayharris.tictactoe.minimax.MinimaxState;
import info.jayharris.tictactoe.minimax.MinimaxUtility;
import info.jayharris.tictactoe.minimax.TictactoeMinimaxDecision;

public class MinimaxPlayer extends Player {

    public MinimaxPlayer(Piece piece) {
        super(piece);
    }

    @Override
    public Move getMove(Tictactoe game) {
        TictactoeMinimaxDecision decision = TictactoeMinimaxDecision.doCreate(new MinimaxState(game.getBoard(), piece));

        return null;
    }
}

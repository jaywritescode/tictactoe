package info.jayharris.tictactoe.player;

import info.jayharris.tictactoe.Board;
import info.jayharris.tictactoe.Move;
import info.jayharris.tictactoe.Piece;
import info.jayharris.tictactoe.Tictactoe;
import info.jayharris.tictactoe.minimax.MinimaxState;
import info.jayharris.tictactoe.minimax.TictactoeMinimaxDecision;

public class MinimaxPlayer extends Player {

    public MinimaxPlayer(Piece piece) {
        super(piece);
    }

    @Override
    public Move getMove(Tictactoe game) {
        MinimaxState state = new MinimaxState(Board.copyFrom(game), getPiece());
        TictactoeMinimaxDecision decision = new TictactoeMinimaxDecision(this);

        return decision.perform(state).getMove();
    }
}

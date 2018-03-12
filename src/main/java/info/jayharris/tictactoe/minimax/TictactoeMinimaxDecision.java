package info.jayharris.tictactoe.minimax;

import info.jayharris.minimax.TwoPlayerMinimaxDecision;
import info.jayharris.tictactoe.Board;
import info.jayharris.tictactoe.Move;
import info.jayharris.tictactoe.Outcome;
import info.jayharris.tictactoe.Piece;

import java.util.function.Function;

public class TictactoeMinimaxDecision extends TwoPlayerMinimaxDecision<MinimaxState> {

    private TictactoeMinimaxDecision(MinimaxState root, Piece piece) {
        super(root, utilityFn(piece));
    }

    static Function<MinimaxState, Long> utilityFn(Piece piece) {
        return state -> {
            if (!state.terminalTest()) {
                return null;
            }

            Outcome outcome = state.getOutcome().get();
            if (outcome == Outcome.tie()) {
                return 0L;
            }
            return outcome.winner() == piece ? Long.MAX_VALUE : Long.MIN_VALUE;
        };
    }

    public static TictactoeMinimaxDecision doCreate(MinimaxState root, Piece piece) {
        return new TictactoeMinimaxDecision(root, piece);
    }

    public static void main(String... args) {
        Board board = new Board();

        board.setPiece(Move.at(1), Piece.O);
        board.setPiece(Move.at(2), Piece.O);
        board.setPiece(Move.at(3), Piece.X);
        board.setPiece(Move.at(4), Piece.X);
        board.setPiece(Move.at(5), Piece.O);
        board.setPiece(Move.at(6), Piece.X);

        MinimaxState state = new MinimaxState(board, Piece.X);

        TictactoeMinimaxDecision decision = TictactoeMinimaxDecision.doCreate(state, Piece.X);

        System.err.println(decision.perform());
    }
}

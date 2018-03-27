package info.jayharris.tictactoe.minimax;

import info.jayharris.minimax.TwoPlayerMinimaxDecision;
import info.jayharris.tictactoe.Board;
import info.jayharris.tictactoe.Move;
import info.jayharris.tictactoe.Outcome;
import info.jayharris.tictactoe.Piece;
import org.apache.commons.lang3.Validate;

public class TictactoeMinimaxDecision extends TwoPlayerMinimaxDecision<MinimaxState, MinimaxAction> {

    private TictactoeMinimaxDecision(MinimaxState root) {
        super(root, state -> {
            Validate.isTrue(state.terminalTest());

            Piece piece = root.getToMove();

            Outcome outcome = state.getOutcome().get();
            if (outcome.isTie()) {
                return 0L;
            }
            return outcome.winner() == piece ? Long.MAX_VALUE : Long.MIN_VALUE;
        });
    }

    /**
     * Creates a minimax decision tree for tictactoe.
     *
     * @param root the current board state
     * @return a decision tree
     */
    public static TictactoeMinimaxDecision doCreate(MinimaxState root) {
        return new TictactoeMinimaxDecision(root);
    }

    public static void main(String... args) {
        Board board = new Board();

        board.setPiece(Move.at(1), Piece.O);
        board.setPiece(Move.at(2), Piece.O);
        board.setPiece(Move.at(3), Piece.X);
        board.setPiece(Move.at(4), Piece.X);
        board.setPiece(Move.at(5), Piece.O);
        board.setPiece(Move.at(6), Piece.X);

        MinimaxState state = new MinimaxState(3, board.iterator(), Piece.X);

        TictactoeMinimaxDecision decision = TictactoeMinimaxDecision.doCreate(state);

        System.err.println(decision.perform());
    }
}

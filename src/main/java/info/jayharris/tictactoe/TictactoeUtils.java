package info.jayharris.tictactoe;

import org.apache.commons.lang3.Validate;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class TictactoeUtils {

    /**
     * Gets the outcome of the game.
     *
     * @param board the game board
     * @return the outcome, if the game is over. Otherwise {@code Optional.empty()}.
     */
    public static Optional<Outcome> getOutcome(Board board) {
        return Optional.ofNullable(board.getAllTicTacToeLines()
                .stream()
                .filter(TictactoeUtils::isWinningLine)
                .findFirst()
                .map(TictactoeUtils::firstPiece)
                .map(Outcome::new)
                .orElse(board.isFull() ? Outcome.tie() : null));
    }

    public static boolean isGameOver(Board board) {
        return board.getAllTicTacToeLines()
                .stream()
                .filter(TictactoeUtils::isWinningLine)
                .findAny()
                .isPresent();
    }

    private static Piece firstPiece(List<Piece> pieces) {
        Validate.notEmpty(pieces);
        return Validate.notNull(pieces.get(0));
    }

    private static boolean isWinningLine(List<Piece> pieces) {
        Validate.notEmpty(pieces);

        Piece first = pieces.get(0);
        return first != null && pieces.stream().allMatch(Predicate.isEqual(first));
    }
}

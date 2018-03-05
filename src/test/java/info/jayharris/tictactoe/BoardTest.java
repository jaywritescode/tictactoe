package info.jayharris.tictactoe;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    @Nested
    @DisplayName("#getOutcome")
    class GetOutcome {

        @Test
        @DisplayName("vertical tic-tac-toe")
        void testVertical() {
            Board board = new Board();
            board.setPiece(Move.at(board, Pair.of(1, 1)), Piece.X);
            board.setPiece(Move.at(board, Pair.of(0, 0)), Piece.O);
            board.setPiece(Move.at(board, Pair.of(0, 2)), Piece.X);
            board.setPiece(Move.at(board, Pair.of(2, 0)), Piece.O);
            board.setPiece(Move.at(board, Pair.of(1, 2)), Piece.X);
            board.setPiece(Move.at(board, Pair.of(1, 0)), Piece.O);

            assertThat(board.getOutcome()).isEqualTo(Optional.of(Outcome.O()));
        }

        @Test
        @DisplayName("horizontal tic-tac-toe")
        void testHorizontal() {
            Board board = new Board();
            board.setPiece(Move.at(board, Pair.of(1, 1)), Piece.X);
            board.setPiece(Move.at(board, Pair.of(0, 2)), Piece.O);
            board.setPiece(Move.at(board, Pair.of(1, 2)), Piece.X);
            board.setPiece(Move.at(board, Pair.of(2, 0)), Piece.O);
            board.setPiece(Move.at(board, Pair.of(1, 0)), Piece.X);

            assertThat(board.getOutcome()).isEqualTo(Optional.of(Outcome.X()));
        }

        @Test
        @DisplayName("diagonal upper-left to lower-right tic-tac-toe")
        void testDiagonalUpperLeftToLowerRight() {
            Board board = new Board();
            board.setPiece(Move.at(board, Pair.of(1, 1)), Piece.X);
            board.setPiece(Move.at(board, Pair.of(0, 2)), Piece.O);
            board.setPiece(Move.at(board, Pair.of(0, 0)), Piece.X);
            board.setPiece(Move.at(board, Pair.of(1, 2)), Piece.O);
            board.setPiece(Move.at(board, Pair.of(2, 0)), Piece.X);
            board.setPiece(Move.at(board, Pair.of(0, 1)), Piece.O);
            board.setPiece(Move.at(board, Pair.of(2, 2)), Piece.X);

            assertThat(board.getOutcome()).isEqualTo(Optional.of(Outcome.X()));
        }

        @Test
        @DisplayName("diagonal lower-left to upper-right tic-tac-toe")
        void testDiagonalLowerLeftToUpperRight() {
            Board board = new Board();
            board.setPiece(Move.at(board, Pair.of(1, 1)), Piece.X);
            board.setPiece(Move.at(board, Pair.of(0, 1)), Piece.O);
            board.setPiece(Move.at(board, Pair.of(0, 2)), Piece.X);
            board.setPiece(Move.at(board, Pair.of(1, 2)), Piece.O);
            board.setPiece(Move.at(board, Pair.of(2, 0)), Piece.X);

            assertThat(board.getOutcome()).isEqualTo(Optional.of(Outcome.X()));
        }

        @Test
        @DisplayName("board full, no winner")
        void testTie() {
            Board board = new Board();
            board.setPiece(Move.at(board, Pair.of(1, 1)), Piece.X);
            board.setPiece(Move.at(board, Pair.of(0, 0)), Piece.O);
            board.setPiece(Move.at(board, Pair.of(0, 2)), Piece.X);
            board.setPiece(Move.at(board, Pair.of(2, 0)), Piece.O);
            board.setPiece(Move.at(board, Pair.of(1, 0)), Piece.X);
            board.setPiece(Move.at(board, Pair.of(1, 2)), Piece.O);
            board.setPiece(Move.at(board, Pair.of(0, 1)), Piece.X);
            board.setPiece(Move.at(board, Pair.of(2, 1)), Piece.O);
            board.setPiece(Move.at(board, Pair.of(2, 2)), Piece.X);

            assertThat(board.getOutcome()).isEqualTo(Optional.of(Outcome.tie()));
        }

        @Test
        @DisplayName("board not full, no winner yet")
        void testContinue() {
            Board board = new Board();
            board.setPiece(Move.at(board, Pair.of(1, 1)), Piece.X);
            board.setPiece(Move.at(board, Pair.of(0, 0)), Piece.O);
            board.setPiece(Move.at(board, Pair.of(0, 2)), Piece.X);
            board.setPiece(Move.at(board, Pair.of(2, 0)), Piece.O);
            board.setPiece(Move.at(board, Pair.of(1, 0)), Piece.X);
            board.setPiece(Move.at(board, Pair.of(1, 2)), Piece.O);
            board.setPiece(Move.at(board, Pair.of(0, 1)), Piece.X);

            assertThat(board.getOutcome().isPresent()).isFalse();
        }
    }
}

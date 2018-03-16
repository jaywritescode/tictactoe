package info.jayharris.tictactoe.minimax;

import info.jayharris.tictactoe.Board;
import info.jayharris.tictactoe.BoardCreator;
import info.jayharris.tictactoe.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.function.LongPredicate;

import static org.assertj.core.api.Assertions.assertThat;

class MinimaxStateTest {

    @Test
    @DisplayName("it collects the open squares as actions")
    void actions() {
        Board board = BoardCreator.create(new Piece[][] {
                new Piece[] { Piece.X,    null, Piece.O },
                new Piece[] { Piece.O, Piece.X,    null },
                new Piece[] {    null,    null,    null }
        });
        MinimaxState state = new MinimaxState(board, Piece.X);

        assertThat(state.actions()).containsExactlyInAnyOrder(
                MinimaxAction.from(1),
                MinimaxAction.from(5),
                MinimaxAction.from(6),
                MinimaxAction.from(7),
                MinimaxAction.from(8));
    }

    @Nested
    @DisplayName("#terminalTest")
    class TerminalTest {
        @Test
        @DisplayName("game not over")
        void gameNotOver() {
            Board board = BoardCreator.create(new Piece[][] {
                    // @formatter:off
                    new Piece[] { Piece.O,    null,    null },
                    new Piece[] {    null, Piece.X, Piece.X },
                    new Piece[] {    null,    null,    null }
                    // @formatter:on
            });

            assertThat(new MinimaxState(board, Piece.O).terminalTest()).isFalse();
        }

        @Test
        @DisplayName("game over with no winner")
        void gameOverNoWinner() {
            Board board = BoardCreator.create(new Piece[][] {
                    new Piece[] { Piece.O, Piece.O, Piece.X },
                    new Piece[] { Piece.X, Piece.X, Piece.O },
                    new Piece[] { Piece.O, Piece.X, Piece.X }
            });

            assertThat(new MinimaxState(board, Piece.O).terminalTest()).isTrue();
        }

        @Test
        @DisplayName("game over with winner")
        void gameOverWithWinner() {
            Board board = BoardCreator.create(new Piece[][] {
                    new Piece[] { Piece.X,    null, Piece.X },
                    new Piece[] {    null, Piece.X, Piece.O },
                    new Piece[] { Piece.O, Piece.O, Piece.X }
            });

            assertThat(new MinimaxState(board, Piece.O).terminalTest()).isTrue();
        }
    }
}
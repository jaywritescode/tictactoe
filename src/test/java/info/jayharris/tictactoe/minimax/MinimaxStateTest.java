package info.jayharris.tictactoe.minimax;

import info.jayharris.tictactoe.Board;
import info.jayharris.tictactoe.BoardCreator;
import info.jayharris.tictactoe.Move;
import info.jayharris.tictactoe.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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
        MinimaxState state = new MinimaxState(3, board.iterator(), Piece.X);

        assertThat(state.actions()).extracting("move").containsExactlyInAnyOrder(
                Move.at(1), Move.at(5), Move.at(6), Move.at(7), Move.at(8));
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

            assertThat(new MinimaxState(3, board.iterator(), Piece.O).terminalTest()).isFalse();
        }

        @Test
        @DisplayName("game over with no winner")
        void gameOverNoWinner() {
            Board board = BoardCreator.create(new Piece[][] {
                    new Piece[] { Piece.O, Piece.O, Piece.X },
                    new Piece[] { Piece.X, Piece.X, Piece.O },
                    new Piece[] { Piece.O, Piece.X, Piece.X }
            });

            assertThat(new MinimaxState(3, board.iterator(), Piece.O).terminalTest()).isTrue();
        }

        @Test
        @DisplayName("game over with winner")
        void gameOverWithWinner() {
            Board board = BoardCreator.create(new Piece[][] {
                    new Piece[] { Piece.X,    null, Piece.X },
                    new Piece[] {    null, Piece.X, Piece.O },
                    new Piece[] { Piece.O, Piece.O, Piece.X }
            });

            assertThat(new MinimaxState(3, board.iterator(), Piece.O).terminalTest()).isTrue();
        }
    }
}
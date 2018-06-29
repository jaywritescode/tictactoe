package info.jayharris.tictactoe.minimax;


import info.jayharris.tictactoe.BoardCreator;
import info.jayharris.tictactoe.Move;
import info.jayharris.tictactoe.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MinimaxStateTest {

    @Test
    @DisplayName("it gets all the legal actions")
    void actions() {
        MinimaxState state = MinimaxState.of(BoardCreator.create(new Piece[][] {
                //@formatter:off
                new Piece[] { null   , Piece.O, null },
                new Piece[] { Piece.X, Piece.X, null },
                new Piece[] {    null,    null, null }
                //@formatter:on
        }), Piece.O, null);

        assertThat(state.actions())
                .extracting("move")
                .containsExactlyInAnyOrder(Move.at(0), Move.at(2), Move.at(5), Move.at(6), Move.at(7), Move.at(8));
    }

    @Nested
    @DisplayName("#utility")
    class Utility {

        @Test
        @DisplayName("game not over")
        void gameNotOver() {
            MinimaxState state = MinimaxState.of(BoardCreator.create(new Piece[][] {
                //@formatter:off
                new Piece[] { null   , Piece.O, null },
                new Piece[] { Piece.X, Piece.X, null },
                new Piece[] {    null,    null, null }
                //@formatter:on
            }), Piece.O, null);

            assertThat(state.utility()).isEmpty();
        }

        @Test
        @DisplayName("player wins")
        void playerWins() {
            MinimaxState state = MinimaxState.of(BoardCreator.create(new Piece[][] {
                    //@formatter:off
                    new Piece[] { Piece.O, Piece.X, Piece.O },
                    new Piece[] { Piece.X, Piece.O,    null },
                    new Piece[] { Piece.O, Piece.X, Piece.X }
                    //@formatter:on
            }), null, Piece.O);

            assertThat(state.utility()).hasValue(MinimaxState.WIN);
        }

        @Test
        @DisplayName("player loses")
        void playerLoses() {
            MinimaxState state = MinimaxState.of(BoardCreator.create(new Piece[][] {
                    //@formatter:off
                    new Piece[] { Piece.O, Piece.X,    null },
                    new Piece[] {    null, Piece.X, Piece.O },
                    new Piece[] {    null, Piece.X,    null }
                    //@formatter:on
            }), null, Piece.O);

            assertThat(state.utility()).hasValue(MinimaxState.LOSS);
        }

        @Test
        @DisplayName("tie game")
        void tieGame() {
            MinimaxState state = MinimaxState.of(BoardCreator.create(new Piece[][] {
                    //@formatter:off
                    new Piece[] { Piece.X, Piece.O, Piece.X },
                    new Piece[] { Piece.O, Piece.X, Piece.X },
                    new Piece[] { Piece.O, Piece.X, Piece.O }
            }), null, Piece.X);

            assertThat(state.utility()).hasValue(MinimaxState.TIE);
        }
    }
}
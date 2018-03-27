package info.jayharris.tictactoe.minimax;

import info.jayharris.tictactoe.Move;
import info.jayharris.tictactoe.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static info.jayharris.tictactoe.minimax.assertions.ProjectAssertions.assertThat;


class TictactoeMinimaxDecisionTest {

    @Nested
    class Perform {
        MinimaxState state;
        TictactoeMinimaxDecision decision;

        @Test
        @DisplayName("it chooses a win over a tie")
        public void testPrefersWinsToTies() {
            state = new MinimaxState(3, Arrays.asList(
                    Piece.X, Piece.O,    null,
                    Piece.X, Piece.O,    null,
                    Piece.O, Piece.X,    null
            ).iterator(), Piece.X);
            decision = TictactoeMinimaxDecision.doCreate(state);

            assertThat(decision.perform()).hasMove(Move.at(2));
        }

        @Test
        @DisplayName("it chooses a tie over a loss")
        public void testPrefersTiesToLosses() {
            state = new MinimaxState(3, Arrays.asList(
                    Piece.X, Piece.O,    null,
                    Piece.X,    null,    null,
                    Piece.O, Piece.X,    null
            ).iterator(), Piece.X);
            decision = TictactoeMinimaxDecision.doCreate(state);

            assertThat(decision.perform().getMove())
                    .isIn(IntStream.of(4, 5, 8).mapToObj(Move::at).collect(Collectors.toSet()));
        }
    }
}
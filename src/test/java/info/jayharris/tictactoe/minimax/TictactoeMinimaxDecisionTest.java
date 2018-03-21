package info.jayharris.tictactoe.minimax;

import info.jayharris.tictactoe.BoardCreator;
import info.jayharris.tictactoe.Move;
import info.jayharris.tictactoe.Piece;
import info.jayharris.tictactoe.minimax.assertions.MinimaxActionAssert;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Set;
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
            state = new MinimaxState(BoardCreator.create(new Piece[][] {
                    new Piece[] {Piece.X, Piece.O,    null},
                    new Piece[] {Piece.X, Piece.O,    null},
                    new Piece[] {Piece.O, Piece.X,    null}
            }), Piece.X);
            decision = TictactoeMinimaxDecision.doCreate(state);

            assertThat(decision.perform()).hasMove(Move.at(2));
        }

        @Test
        @DisplayName("it chooses a tie over a loss")
        public void testPrefersTiesToLosses() {
            state = new MinimaxState(BoardCreator.create(new Piece[][] {
                    new Piece[] {Piece.X, Piece.O,    null},
                    new Piece[] {Piece.X,    null,    null},
                    new Piece[] {Piece.O, Piece.X,    null}
            }), Piece.X);
            decision = TictactoeMinimaxDecision.doCreate(state);

            assertThat(decision.perform().getMove())
                    .isIn(IntStream.of(4, 5, 8).mapToObj(Move::at).collect(Collectors.toSet()));
        }
    }
}
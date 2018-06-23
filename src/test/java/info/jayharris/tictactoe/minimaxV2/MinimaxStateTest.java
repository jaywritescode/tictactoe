package info.jayharris.tictactoe.minimaxV2;


import info.jayharris.tictactoe.BoardCreator;
import info.jayharris.tictactoe.Move;
import info.jayharris.tictactoe.Piece;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MinimaxStateTest {

    @Test
    @DisplayName("it gets all the legal actions")
    void actions() {
        MinimaxState state = MinimaxState.of(BoardCreator.create(new Piece[][] {
                //@formatter:off
                new Piece[] { null   , Piece.O, null },
                new Piece[] { Piece.X, Piece.X, null },
                new Piece[] {    null,    null, null }
        }), Piece.O);

        assertThat(state.actions())
                .extracting("move")
                .containsExactlyInAnyOrder(Move.at(0), Move.at(2), Move.at(5), Move.at(6), Move.at(7), Move.at(8));
    }
}
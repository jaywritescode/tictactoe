package info.jayharris.tictactoe.minimax;

import info.jayharris.tictactoe.BoardCreator;
import info.jayharris.tictactoe.Move;
import info.jayharris.tictactoe.Piece;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MinimaxActionTest {

    @Test
    void apply() {
        MinimaxState initial = MinimaxState.of(BoardCreator.create(new Piece[][] {
                //@formatter:off
                new Piece[] { null   , null   , Piece.O },
                new Piece[] { Piece.O, Piece.X, Piece.X },
                new Piece[] {   null,     null,    null }
                //@formatter:on
        }), Piece.X, Piece.X);

        MinimaxState expected = MinimaxState.of(BoardCreator.create(new Piece[][] {
                //@formatter:off
                new Piece[] { null   , null   , Piece.O },
                new Piece[] { Piece.O, Piece.X, Piece.X },
                new Piece[] {   null,     null, Piece.X }
                //@formatter:on
        }), Piece.O, Piece.X);

        assertThat(MinimaxAction.from(Move.at(8)).apply(initial)).isEqualToComparingFieldByField(expected);
        assertThat(initial).isNotSameAs(expected);
    }
}
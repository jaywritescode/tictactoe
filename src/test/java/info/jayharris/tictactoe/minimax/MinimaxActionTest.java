package info.jayharris.tictactoe.minimax;

import info.jayharris.tictactoe.Board;
import info.jayharris.tictactoe.BoardCreator;
import info.jayharris.tictactoe.Piece;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MinimaxActionTest {

    @Test
    void apply() {
        Board board = BoardCreator.create(new Piece[][] {
                new Piece[] { null, null, null, null, null },
                new Piece[] { null, null, null, null, null },
                new Piece[] { null, null, null, null, null },
                new Piece[] { null, null, null, null, null },
                new Piece[] { null, null, null, null, null }
        });

        MinimaxState state = new MinimaxState(board, Piece.X);
        MinimaxAction action = MinimaxAction.from(12);

        Board successor = BoardCreator.create(new Piece[][] {
                new Piece[] {    null,    null,    null,    null,    null },
                new Piece[] {    null,    null,    null,    null,    null },
                new Piece[] {    null,    null, Piece.X,    null,    null },
                new Piece[] {    null,    null,    null,    null,    null },
                new Piece[] {    null,    null,    null,    null,    null }
        });

        assertThat(action.apply(state)).isEqualTo(new MinimaxState(successor, Piece.O));
    }
}
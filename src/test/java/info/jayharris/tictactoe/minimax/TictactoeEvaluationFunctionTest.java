package info.jayharris.tictactoe.minimax;

import info.jayharris.tictactoe.BoardCreator;
import info.jayharris.tictactoe.Piece;
import info.jayharris.tictactoe.player.MinimaxPlayer;
import info.jayharris.tictactoe.player.Player;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TictactoeEvaluationFunctionTest {

    Player player = new MinimaxPlayer(Piece.O);

    @Test
    @DisplayName("player wins")
    void apply_playerWins() {
        MinimaxState state = new MinimaxState(BoardCreator.create(new Piece[][] {
                //@formatter:off
                    new Piece[] { Piece.O, Piece.X, Piece.O },
                    new Piece[] { Piece.X, Piece.O,    null },
                    new Piece[] { Piece.O, Piece.X, Piece.X }
                    //@formatter:on
        }), null);

        TictactoeEvaluationFunction eval = TictactoeEvaluationFunction.create(player);
        assertThat(eval.apply(state)).isEqualTo(TictactoeEvaluationFunction.WIN);
    }

    @Test
    @DisplayName("player loses")
    void apply_playerLoses() {
        MinimaxState state = new MinimaxState(BoardCreator.create(new Piece[][] {
                //@formatter:off
                    new Piece[] { Piece.O, Piece.X,    null },
                    new Piece[] {    null, Piece.X, Piece.O },
                    new Piece[] {    null, Piece.X,    null }
                    //@formatter:on
        }), null);

        TictactoeEvaluationFunction eval = TictactoeEvaluationFunction.create(player);
        assertThat(eval.apply(state)).isEqualTo(TictactoeEvaluationFunction.LOSS);
    }

    @Test
    @DisplayName("tie game")
    void tieGame() {
        MinimaxState state = new MinimaxState(BoardCreator.create(new Piece[][] {
                //@formatter:off
                    new Piece[] { Piece.X, Piece.O, Piece.X },
                    new Piece[] { Piece.O, Piece.X, Piece.X },
                    new Piece[] { Piece.O, Piece.X, Piece.O }
                    //@formatter:on
        }), null);

        TictactoeEvaluationFunction eval = TictactoeEvaluationFunction.create(player);
        assertThat(eval.apply(state)).isEqualTo(TictactoeEvaluationFunction.TIE);
    }
}
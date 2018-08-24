package info.jayharris.tictactoe.player;

import info.jayharris.tictactoe.Move;
import info.jayharris.tictactoe.Piece;
import info.jayharris.tictactoe.Tictactoe;
import info.jayharris.tictactoe.TictactoeBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.Mockito.*;


public class TerminalPlayerTest {

    TerminalPlayer x, o;
    Tictactoe game;

    @Mock private BufferedReader xIn;
    @Mock private BufferedReader oIn;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        x = new TerminalPlayer(Piece.X, xIn, System.err);
        o = new TerminalPlayer(Piece.O, oIn, System.err);

        game = new Tictactoe(x, o);
    }

    @Nested
    @DisplayName("#getMove")
    class GetMove {

        @Test
        @DisplayName("it gets a legal move")
        void testLegalMove() throws Exception {
            when(xIn.readLine()).thenReturn("a1");
            assertThat(x.getMove(game)).isEqualToComparingFieldByField(Move.at(6));
        }

        @Test
        @DisplayName("it rejects algebraic notation with length less than two")
        void testTooShort() throws Exception {
            when(xIn.readLine()).thenReturn("", "b", "c2");

            x.getMove(game);
            verify(xIn, times(3)).readLine();
        }

        @Test
        @DisplayName("it rejects invalid files")
        void testInvalidFile() throws Exception {
            when(xIn.readLine()).thenReturn("71", "e3", "A1");

            x.getMove(game);
            verify(xIn, times(3)).readLine();
        }

        @Test
        @DisplayName("it rejects invalid ranks")
        void testInvalidRank() throws Exception {
            game = new Tictactoe(x, o, 12);

            when(xIn.readLine()).thenReturn("b0", "a20", "fd", "H12");

            x.getMove(game);
            verify(xIn, times(4)).readLine();
        }

        @Test
        @DisplayName("it rejects illegal moves")
        void testIllegalMove() throws Exception {
            game = new TictactoeBuilder()
                    .setPlayer(x)
                    .setAllMoves(Move.at(4))
                    .build();

            when(xIn.readLine()).thenReturn("b2", "c1");

            x.getMove(game);
            verify(xIn, times(2)).readLine();
        }
    }
}
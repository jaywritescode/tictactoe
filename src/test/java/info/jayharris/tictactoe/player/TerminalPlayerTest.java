package info.jayharris.tictactoe.player;

import info.jayharris.tictactoe.Move;
import info.jayharris.tictactoe.Piece;
import info.jayharris.tictactoe.Tictactoe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;

import static org.assertj.core.api.Assertions.assertThat;
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
            when(xIn.readLine()).thenReturn("2,1");
            assertThat(x.getMove(game)).isEqualToComparingFieldByField(Move.at(7));
        }

        @Test
        @DisplayName("it handles an unparseable string")
        void testUnparseable() throws Exception {
            when(xIn.readLine()).thenReturn("abc", "2,1");

            x.getMove(game);
            verify(xIn, times(2)).readLine();
        }

        @Test
        @DisplayName("it handles numbers that are too big for the game board")
        void testIndexOutOfBounds() throws Exception {
            when(xIn.readLine()).thenReturn("3,1", "2,1");

            x.getMove(game);
            verify(xIn, times(2)).readLine();
        }
    }
}
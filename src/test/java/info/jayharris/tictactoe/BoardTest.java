package info.jayharris.tictactoe;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class BoardTest {

    @Test
    @DisplayName("#getPiece")
    void testGetPiece() {
        Board board = Board.empty(4);

        board.setPiece(Move.at(5), Piece.X);
        board.setPiece(Move.at(13), Piece.O);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(board.getPiece(5)).isEqualTo(Piece.X);
        softly.assertThat(board.getPiece(13)).isEqualTo(Piece.O);
        softly.assertThat(board.getPiece(4)).isNull();
        softly.assertAll();
    }

    @Nested
    @DisplayName("#setPiece")
    @TestInstance(Lifecycle.PER_CLASS)
    class SetPiece {

        Board board;

        @BeforeAll
        void init() {
            board = Board.empty(3);

            board.setPiece(Move.at(0), Piece.X);
        }

        @Test
        @DisplayName("adds a piece to the board")
        void testSetPiece() {
            board.setPiece(Move.at(3), Piece.O);

            assertThat(board.getPiece(0)).isEqualTo(Piece.X);
            assertThat(board.getPiece(3)).isEqualTo(Piece.O);
        }

        @Test
        @DisplayName("can't put a piece in an occupied square")
        void testSquareIsOccupied() {
            assertThatIllegalArgumentException().isThrownBy(() -> board.setPiece(Move.at(0), Piece.O));
        }
    }

    @Test
    @DisplayName("#isOccupied")
    void testIsOccupied() {
        Board board = Board.empty(3);

        board.setPiece(Move.at(8), Piece.O);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(board.isOccupied(8)).isTrue();
        softly.assertThat(board.isOccupied(6)).isFalse();
        softly.assertAll();
    }

    @Nested
    @DisplayName("#isFull")
    class IsFull {

        Board board;

        @BeforeEach
        void setUp() {
            board = Board.empty(3);
        }

        @Test
        @DisplayName("board is full")
        void testIsFull() {
            board.setPiece(Move.at(0), Piece.X);
            board.setPiece(Move.at(1), Piece.X);
            board.setPiece(Move.at(2), Piece.O);
            board.setPiece(Move.at(3), Piece.O);
            board.setPiece(Move.at(4), Piece.O);
            board.setPiece(Move.at(5), Piece.X);
            board.setPiece(Move.at(6), Piece.O);
            board.setPiece(Move.at(7), Piece.X);
            board.setPiece(Move.at(8), Piece.O);

            assertThat(board.isFull()).isTrue();
        }

        @Test
        @DisplayName("board is not full")
        void testIsNotFull() {
            board.setPiece(Move.at(0), Piece.X);
            board.setPiece(Move.at(1), Piece.X);
            board.setPiece(Move.at(2), Piece.O);
            board.setPiece(Move.at(3), Piece.O);
            board.setPiece(Move.at(4), Piece.O);
            board.setPiece(Move.at(6), Piece.O);
            board.setPiece(Move.at(7), Piece.X);
            board.setPiece(Move.at(8), Piece.O);

            assertThat(board.isFull()).isFalse();
        }
    }


    @Test
    @DisplayName("#isFull")
    void testIsFull() {
        Board board = Board.empty(3);

        board.setPiece(Move.at(0), Piece.X);
        board.setPiece(Move.at(1), Piece.X);
        board.setPiece(Move.at(2), Piece.O);
        board.setPiece(Move.at(3), Piece.X);
        board.setPiece(Move.at(4), Piece.O);
        board.setPiece(Move.at(5), Piece.O);
        board.setPiece(Move.at(6), Piece.X);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(board.isFull()).isFalse();

        board.setPiece(Move.at(7), Piece.O);
        board.setPiece(Move.at(8), Piece.X);
    }

    @Test
    @DisplayName("#getAllTicTacToeLines")
    void testGetAllTicTacToeLines() {
        Board board = Board.empty(4);

        /*
            X | O | O | X
           ---+---+---+---
            O | X |   |
           ---+---+---+---
              |   | X |
           ---+---+---+---
              |   |   | O
         */
        board.setPiece(Move.at(0), Piece.X);
        board.setPiece(Move.at(1), Piece.O);
        board.setPiece(Move.at(2), Piece.O);
        board.setPiece(Move.at(3), Piece.X);
        board.setPiece(Move.at(4), Piece.O);
        board.setPiece(Move.at(5), Piece.X);
        board.setPiece(Move.at(10), Piece.X);
        board.setPiece(Move.at(15), Piece.O);

        List<List<Piece>> lines = board.getAllTicTacToeLines();

        assertThat(lines).containsExactlyInAnyOrder(
                Arrays.asList(Piece.X, Piece.O, Piece.O, Piece.X),
                Arrays.asList(Piece.O, Piece.X, null, null),
                Arrays.asList(null, null, Piece.X, null),
                Arrays.asList(null, null, null, Piece.O),
                Arrays.asList(Piece.X, Piece.O, null, null),
                Arrays.asList(Piece.O, Piece.X, null, null),
                Arrays.asList(Piece.O, null, Piece.X, null),
                Arrays.asList(Piece.X, null, null, Piece.O),
                Arrays.asList(Piece.X, Piece.X, Piece.X, Piece.O),
                Arrays.asList(Piece.X, null, null, null));
    }

    @Nested
    class Rotate {

        @Test
        @DisplayName("board with odd size")
        void rotate3() {
            Board board = BoardCreator.create(new Piece[] {
                    Piece.X, Piece.O, null, null, Piece.O, Piece.X, null, null, null}, 3);
            /*
                X | O |           |   | X
               ---+---+---     ---+---+---
                  | O | X  ==>    | O | O
               ---+---+---     ---+---+---
                  |   |           | X |
             */

            board.rotate();
            assertThat(board).hasFieldOrPropertyWithValue(
                    "pieces", Arrays.asList(null, null, Piece.X, null, Piece.O, Piece.O, null, Piece.X, null));
        }

        @Test
        @DisplayName("board with even size")
        void rotate4() {
            Board board = BoardCreator.create(new Piece[][] {
                    new Piece[] {Piece.X, Piece.O, Piece.O, Piece.X},
                    new Piece[] {Piece.O, Piece.X,  null  ,  null  },
                    new Piece[] { null  ,  null  , Piece.X,  null  },
                    new Piece[] { null  ,  null  ,  null  , Piece.O}
            });
            /*
               X | O | O | X         |   | O | X
              ---+---+---+---     ---+---+---+---
               O | X |   |           |   | X | O
              ---+---+---+--- ==> ---+---+---+---
                 |   | X |           | X |   | O
              ---+---+---+---     ---+---+---+---
                 |   |   | O       O |   |   | X
            */

            board.rotate();
            assertThat(board).hasFieldOrPropertyWithValue(
                    "pieces", Arrays.asList(null, null, Piece.O, Piece.X, null, null, Piece.X,
                                            Piece.O, null, Piece.X, null, Piece.O, Piece.O, null, null, Piece.X));
        }
    }
}

package info.jayharris.tictactoe;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.util.Arrays;
import java.util.List;

import static info.jayharris.tictactoe.Piece.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class BoardTest {

    @Test
    @DisplayName("#getPiece")
    void testGetPiece() {
        Board board = Board.empty(4);

        board.setPiece(Move.at(5), X);
        board.setPiece(Move.at(13), O);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(board.getPiece(5)).isEqualTo(X);
        softly.assertThat(board.getPiece(13)).isEqualTo(O);
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

            board.setPiece(Move.at(0), X);
        }

        @Test
        @DisplayName("adds a piece to the board")
        void testSetPiece() {
            board.setPiece(Move.at(3), O);

            assertThat(board.getPiece(0)).isEqualTo(X);
            assertThat(board.getPiece(3)).isEqualTo(O);
        }

        @Test
        @DisplayName("can't put a piece in an occupied square")
        void testSquareIsOccupied() {
            assertThatIllegalArgumentException().isThrownBy(() -> board.setPiece(Move.at(0), O));
        }
    }

    @Test
    @DisplayName("#isOccupied")
    void testIsOccupied() {
        Board board = Board.empty(3);

        board.setPiece(Move.at(8), O);

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
            board.setPiece(Move.at(0), X);
            board.setPiece(Move.at(1), X);
            board.setPiece(Move.at(2), O);
            board.setPiece(Move.at(3), O);
            board.setPiece(Move.at(4), O);
            board.setPiece(Move.at(5), X);
            board.setPiece(Move.at(6), O);
            board.setPiece(Move.at(7), X);
            board.setPiece(Move.at(8), O);

            assertThat(board.isFull()).isTrue();
        }

        @Test
        @DisplayName("board is not full")
        void testIsNotFull() {
            board.setPiece(Move.at(0), X);
            board.setPiece(Move.at(1), X);
            board.setPiece(Move.at(2), O);
            board.setPiece(Move.at(3), O);
            board.setPiece(Move.at(4), O);
            board.setPiece(Move.at(6), O);
            board.setPiece(Move.at(7), X);
            board.setPiece(Move.at(8), O);

            assertThat(board.isFull()).isFalse();
        }
    }


    @Test
    @DisplayName("#isFull")
    void testIsFull() {
        Board board = Board.empty(3);

        board.setPiece(Move.at(0), X);
        board.setPiece(Move.at(1), X);
        board.setPiece(Move.at(2), O);
        board.setPiece(Move.at(3), X);
        board.setPiece(Move.at(4), O);
        board.setPiece(Move.at(5), O);
        board.setPiece(Move.at(6), X);

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(board.isFull()).isFalse();

        board.setPiece(Move.at(7), O);
        board.setPiece(Move.at(8), X);
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
        board.setPiece(Move.at(0), X);
        board.setPiece(Move.at(1), O);
        board.setPiece(Move.at(2), O);
        board.setPiece(Move.at(3), X);
        board.setPiece(Move.at(4), O);
        board.setPiece(Move.at(5), X);
        board.setPiece(Move.at(10), X);
        board.setPiece(Move.at(15), O);

        List<List<Piece>> lines = board.getAllTicTacToeLines();

        assertThat(lines).containsExactlyInAnyOrder(
                Arrays.asList(X, O, O, X),
                Arrays.asList(O, X, null, null),
                Arrays.asList(null, null, X, null),
                Arrays.asList(null, null, null, O),
                Arrays.asList(X, O, null, null),
                Arrays.asList(O, X, null, null),
                Arrays.asList(O, null, X, null),
                Arrays.asList(X, null, null, O),
                Arrays.asList(X, X, X, O),
                Arrays.asList(X, null, null, null));
    }

    @Nested
    class Rotate {

        @Test
        @DisplayName("board with odd size")
        void rotate3() {
            Board board = BoardCreator.create(new Piece[] {X, O, null, null, O, X, null, null, null}, 3);
            /*
                X | O |           |   | X
               ---+---+---     ---+---+---
                  | O | X  ==>    | O | O
               ---+---+---     ---+---+---
                  |   |           | X |
             */

            board.rotate();
            assertThat(board).hasFieldOrPropertyWithValue(
                    "pieces", Arrays.asList(null, null, X, null, O, O, null, X, null));
        }

        @Test
        @DisplayName("board with even size")
        void rotate4() {
            Board board = BoardCreator.create(new Piece[][] {
                    new Piece[] { X  ,  O  ,  O  ,  X  },
                    new Piece[] { O  ,  X  , null, null},
                    new Piece[] {null, null,  X  , null},
                    new Piece[] {null, null, null,  O  }
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
                    "pieces", Arrays.asList(
                            null, null, O, X, null, null, X, O, null, X, null, O, O, null, null, X));
        }
    }

    @Nested
    class Reflect {

        @Nested
        class ReflectOverHorizontalAxis {

            @Test
            @DisplayName("board with odd size")
            void reflect3() {

            }

            @Test
            @DisplayName("board with even size")
            void reflect4() {

            }
        }

        @Nested
        class ReflectOverVerticalAxis {

            @Test
            @DisplayName("board with odd size")
            void reflect3() {
                Board board = BoardCreator.create(new Piece[] {X, O, X, null, O, X, O, null, null}, 3);
                 /*
                    X | O | X       X | O | X
                   ---+---+---     ---+---+---
                      | O | X  ==>  X | O |
                   ---+---+---     ---+---+---
                    O |   |           |   | O
                 */

                 board.reflectOverVerticalAxis();
                 assertThat(board).hasFieldOrPropertyWithValue(
                         "pieces", Arrays.asList(X, O, X, X, O, null, null, null, O));
            }

            @Test
            @DisplayName("board with even size")
            void reflect4() {
                Board board = BoardCreator.create(new Piece[][] {
                        new Piece[] {null,  X  , null, null},
                        new Piece[] { O  ,  X  ,  O  , null},
                        new Piece[] { X  ,  O  ,  X  ,  O  },
                        new Piece[] {null, null, null,  X  }
                });
                /*
                       | X |   |           |   | X |
                    ---+---+---+---     ---+---+---+---
                     O | X | O |           | O | X | O
                    ---+---+---+--- ==> ---+---+---+---
                     X | O | X | O       O | X | O | X
                    ---+---+---+---     ---+---+---+---
                       |   |   | X       X |   |   |
                 */

                board.reflectOverVerticalAxis();
                assertThat(board).hasFieldOrPropertyWithValue(
                        "pieces", Arrays.asList(null, null, X, null, null, O, X, O, O, X, O, X, X, null, null, null));
            }
        }
    }
}

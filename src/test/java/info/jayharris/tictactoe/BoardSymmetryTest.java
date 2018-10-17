package info.jayharris.tictactoe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static info.jayharris.tictactoe.Piece.O;
import static info.jayharris.tictactoe.Piece.X;
import static org.assertj.core.api.Assertions.assertThat;

class BoardSymmetryTest {

    Board board;
    BoardSymmetry symmetry = BoardSymmetry.instance();

    @BeforeEach
    void setUp() {
        board = BoardCreator.create(new Piece[][] {
                new Piece[] {  X  ,  X  ,  O  , null },
                new Piece[] { null,  O  , null, null },
                new Piece[] { null,  O  ,  O  ,  O   },
                new Piece[] {  X  ,  X  , null,  X   }
        });
    }

    @Test
    @DisplayName("a board is equivalent to itself")
    void testIdentity() {
        assertThat(BoardSymmetry.create(board)).isEqualTo(symmetry.wrap(board));
    }

    @Test
    @DisplayName("a board is equivalent its 90° rotation")
    void testRotateClockwise() {
        Board rotated = Board.copyFrom(board).rotate();
        assertThat(BoardSymmetry.create(board)).isEqualTo(symmetry.wrap(rotated));
    }

    @Test
    @DisplayName("a board is equivalent its 180° rotation")
    void testRotateAround() {
        Board rotated = Board.copyFrom(board).rotate().rotate();
        assertThat(BoardSymmetry.create(board)).isEqualTo(symmetry.wrap(rotated));
    }

    @Test
    @DisplayName("a board is equivalent its 270° rotation")
    void testRotateCounterClockwise() {
        Board rotated = Board.copyFrom(board).rotate().rotate().rotate();
        assertThat(BoardSymmetry.create(board)).isEqualTo(symmetry.wrap(rotated));
    }

    @Test
    @DisplayName("a board is equivalent to its reflection over the horizontal axis")
    void testReflectOverHorizontalAxis() {
        Board reflected = Board.copyFrom(board).reflectOverHorizontalAxis();
        assertThat(BoardSymmetry.create(board)).isEqualTo(symmetry.wrap(reflected));
    }

    @Test
    @DisplayName("a board is equivalent to its reflection over the vertical axis")
    void testReflectOverVerticalAxis() {
        Board reflected = Board.copyFrom(board).reflectOverVerticalAxis();
        assertThat(BoardSymmetry.create(board)).isEqualTo(symmetry.wrap(reflected));
    }

    @Test
    @DisplayName("a board is equivalent to its reflection over the northwest-southeast axis")
    void testReflectOverNorthwestSoutheastAxis() {
        Board reflected = Board.copyFrom(board).reflectOverNorthwestSoutheastAxis();
        assertThat(BoardSymmetry.create(board)).isEqualTo(symmetry.wrap(reflected));
    }

    @Test
    @DisplayName("a board is equivalent to its reflection over the northeast-southwest axis")
    void testReflectOverNortheastSouthwestAxis() {
        Board reflected = Board.copyFrom(board).reflectOverNortheastSouthwestAxis();
        assertThat(BoardSymmetry.create(board)).isEqualTo(symmetry.wrap(reflected));
    }

    @Test
    @DisplayName("non-symmetrical boards are not equivalent")
    void testNonEquivalence() {
        Board other = BoardCreator.create(new Piece[][] {
                new Piece[] {  X  ,  X  ,  O  , null },
                new Piece[] { null,  O  , null, null },
                new Piece[] { null,  O  ,  X  ,  O   },
                new Piece[] {  X  ,  X  , null,  X   }
        });
        assertThat(BoardSymmetry.create(board)).isNotEqualTo(symmetry.wrap(other));
    }
}
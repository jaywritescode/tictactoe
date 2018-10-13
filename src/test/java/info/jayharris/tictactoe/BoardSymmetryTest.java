package info.jayharris.tictactoe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static info.jayharris.tictactoe.Piece.O;
import static info.jayharris.tictactoe.Piece.X;
import static org.assertj.core.api.Assertions.assertThat;

class BoardSymmetryTest {

    Board board;
    BoardSymmetry symmetry = BoardSymmetry.create();

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
        assertThat(symmetry.doEquivalent(board, board)).isTrue();
    }

    @Test
    @DisplayName("a board is equivalent its 90° rotation")
    void testRotateClockwise() {
        Board rotated = Board.copyFrom(board).rotate();
        assertThat(symmetry.doEquivalent(board, rotated)).isTrue();
    }

    @Test
    @DisplayName("a board is equivalent its 180° rotation")
    void testRotateAround() {
        Board rotated = Board.copyFrom(board).rotate().rotate();
        assertThat(symmetry.doEquivalent(board, rotated)).isTrue();
    }

    @Test
    @DisplayName("a board is equivalent its 270° rotation")
    void testRotateCounterClockwise() {
        Board rotated = Board.copyFrom(board).rotate().rotate().rotate();
        assertThat(symmetry.doEquivalent(board, rotated)).isTrue();
    }

    @Test
    @DisplayName("a board is equivalent to its reflection over the horizontal axis")
    void testReflectOverHorizontalAxis() {
        Board reflected = Board.copyFrom(board).reflectOverHorizontalAxis();
        assertThat(symmetry.doEquivalent(board, reflected)).isTrue();
    }

    @Test
    @DisplayName("a board is equivalent to its reflection over the vertical axis")
    void testReflectOverVerticalAxis() {
        Board reflected = Board.copyFrom(board).reflectOverVerticalAxis();
        assertThat(symmetry.doEquivalent(board, reflected)).isTrue();
    }
}
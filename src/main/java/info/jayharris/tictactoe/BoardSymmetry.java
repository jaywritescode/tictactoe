package info.jayharris.tictactoe;

import com.google.common.base.Equivalence;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BoardSymmetry extends Equivalence<Board> {

    @Override
    protected boolean doEquivalent(Board a, Board b) {
        return rotationsAndReflections(a).contains(b);
    }

    @Override
    protected int doHash(Board board) {
        return Objects.hash(rotationsAndReflections(board));
    }

    private static Set<Board> rotationsAndReflections(Board board) {
        Set<Board> symmetries = new HashSet<>();

        Board tmp;

        symmetries.add(board);
        symmetries.add(tmp = Board.copyFrom(board).rotate());
        symmetries.add(tmp = Board.copyFrom(tmp).rotate());
        symmetries.add(Board.copyFrom(tmp).rotate());
        symmetries.add(Board.copyFrom(board).reflectOverHorizontalAxis());
        symmetries.add(Board.copyFrom(board).reflectOverVerticalAxis());

        return symmetries;
    }

    public static BoardSymmetry create() {
        return new BoardSymmetry();
    }
}

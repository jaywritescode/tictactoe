package info.jayharris.tictactoe;

import com.google.common.base.Equivalence;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BoardSymmetry extends Equivalence<Board> {

    private static BoardSymmetry singleton = null;

    private BoardSymmetry() { };

    public static BoardSymmetry instance() {
        if (singleton == null) {
            singleton = new BoardSymmetry();
        }

        return singleton;
    }

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
        symmetries.add(Board.copyFrom(board).reflectOverNorthwestSoutheastAxis());
        symmetries.add(Board.copyFrom(board).reflectOverNortheastSouthwestAxis());

        return symmetries;
    }

    public static BoardSymmetry.Wrapper<Board> create(Board board) {
        return BoardSymmetry.instance().wrap(board);
    }
}

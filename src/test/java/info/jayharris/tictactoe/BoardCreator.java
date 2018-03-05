package info.jayharris.tictactoe;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.IntStream;

public class BoardCreator {

    public static Board create(Piece[] pieces) {
        return create(pieces, pieces.length);
    }

    public static Board create(Piece[] pieces, int size) {
        Board board = new Board();

        IntStream.range(0, size)
                .filter(it -> pieces[it] != null)
                .forEach(it -> board.setPiece(Move.at(it), pieces[it]));
        return board;
    }
}

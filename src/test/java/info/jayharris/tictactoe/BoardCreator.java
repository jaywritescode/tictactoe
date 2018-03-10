package info.jayharris.tictactoe;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardCreator {

    @Deprecated
    public static Board create(Piece[] pieces) {
        return create(pieces, pieces.length);
    }

    public static Board create(Piece[][] pieces) {
        return create(Arrays.stream(pieces)
                .flatMap(Arrays::stream)
                .collect(Collectors.toList())
                .toArray(new Piece[] {}), pieces.length);
    }

    public static Board create(Piece[] pieces, int size) {
        Board board = new Board(size);

        IntStream.range(0, size * size)
                .filter(it -> pieces[it] != null)
                .forEach(it -> board.setPiece(Move.at(it), pieces[it]));
        return board;
    }
}

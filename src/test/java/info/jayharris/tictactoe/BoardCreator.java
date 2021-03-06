package info.jayharris.tictactoe;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardCreator {

    public static Board create(Piece[][] pieces) {
        return create(Arrays.stream(pieces)
                .flatMap(Arrays::stream)
                .collect(Collectors.toList())
                .toArray(new Piece[] {}), pieces.length);
    }

    public static Board create(Piece[] pieces, int size) {
        Board board = Board.empty(size);

        IntStream.range(0, size * size)
                .filter(it -> pieces[it] != null)
                .forEach(it -> board.setPiece(Move.at(it), pieces[it]));
        return board;
    }
}

package info.jayharris.tictactoe;

import org.apache.commons.collections4.iterators.ArrayIterator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board extends SquareGrid {

    private final Piece[] pieces;

    public Board() {
        this(3);
    }

    public Board(int size) {
        super(size);
        pieces = new Piece[numSquares()];
    }

    @Override
    public Piece getPiece(int index) {
        return pieces[index];
    }

    public void setPiece(Move move, Piece piece) {
        Validate.notNull(piece);

        int index;
        Validate.isTrue(!isOccupied(index = move.getIndex()));

        pieces[index] = piece;
    }

    @Override
    public boolean isOccupied(int index) {
        return Objects.nonNull(getPiece(index));
    }

    @Override
    public boolean isFull() {
        return IntStream.range(0, numSquares()).allMatch(this::isOccupied);
    }

    public Iterator<Piece> iterator() {
        return new ArrayIterator(pieces);
    }

    public String pretty() {
        String d = String.format("\n%s\n", IntStream.range(0, getSize()).mapToObj(i -> "-").collect(Collectors.joining("+")));

        return IntStream.range(0, getSize())
                .mapToObj(row -> IntStream.range(row * getSize(), (row + 1) * getSize())
                        .mapToObj(index -> Optional.ofNullable(pieces[index]).map(Piece::toString).orElse(StringUtils.SPACE))
                        .collect(Collectors.joining("|")))
                .collect(Collectors.joining(d));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Arrays.equals(pieces, board.pieces);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(pieces);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Board{");
        sb.append("SIZE=").append(SIZE);
        sb.append(", pieces=").append(pieces == null ? "null" : Arrays.asList(pieces).toString());
        sb.append('}');
        return sb.toString();
    }
}
package info.jayharris.tictactoe;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.*;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Board {

    final int SIZE;
    private final ArrayList<Piece> pieces;

    Board() {
        this(3);
    }

    Board(int size) {
        SIZE = size;

        Vector<Piece> tmp = new Vector<>();
        tmp.setSize(SIZE * SIZE);
        pieces = new ArrayList<>(tmp);
    }

    Piece getPiece(int index) {
        return pieces.get(index);
    }

    void setPiece(Move move, Piece piece) {
        Validate.notNull(piece);

        int index;
        Validate.isTrue(!isOccupied(index = move.getIndex()));

        pieces.set(index, piece);
    }

    boolean isOccupied(int index) {
        return Objects.nonNull(getPiece(index));
    }

    boolean isFull() {
        return pieces.stream().noneMatch(Objects::isNull);
    }

    public Iterator<Piece> iterator() {
        return pieces.iterator();
    }

    Set<Move> legalMoves() {
        return IntStream.range(0, pieces.size())
                .filter(i -> pieces.get(i) == null)
                .mapToObj(Move::at)
                .collect(Collectors.toSet());
    }

    private List<Piece> getPiecesInRow(int row) {
        return IntStream.range(0, SIZE)
                .map(i -> row * SIZE + i)
                .mapToObj(pieces::get)
                .collect(Collectors.toList());
    }

    private List<Piece> getPiecesInColumn(int column) {
        return IntStream.range(0, SIZE)
                .map(i -> column + SIZE * i)
                .mapToObj(pieces::get)
                .collect(Collectors.toList());
    }

    private List<Piece> getPiecesInUpperLeftToLowerRightDiag() {
        return IntStream.range(0, SIZE)
                .map(i -> i * SIZE + i)
                .mapToObj(pieces::get)
                .collect(Collectors.toList());
    }

    private List<Piece> getPiecesInUpperRightToLowerLeftDiag() {
        return IntStream.range(0, SIZE)
                .map(i -> (i + 1) * SIZE - (i + 1))
                .mapToObj(pieces::get)
                .collect(Collectors.toList());
    }

    List<List<Piece>> getAllTicTacToeLines() {
        ImmutableList.Builder<List<Piece>> b = ImmutableList.builder();

        IntStream.range(0, SIZE).forEach(i -> {
            b.add(getPiecesInRow(i));
            b.add(getPiecesInColumn(i));
        });

        b.add(getPiecesInUpperLeftToLowerRightDiag());
        b.add(getPiecesInUpperRightToLowerLeftDiag());
        return b.build();
    }

    private Stream<Piece> getPiecesFromToIntFunction(IntUnaryOperator op) {
        return IntStream.range(0, SIZE).map(op).mapToObj(pieces::get);
    }

    String pretty() {
        String separatorLine = "\n" +
                               Collections.nCopies(SIZE, "-").stream().collect(Collectors.joining("+")) +
                               '\n';

        return IntStream.range(0, SIZE)
                .mapToObj(i -> getPiecesInRow(i)
                        .stream()
                        .map(piece -> piece == null ? StringUtils.SPACE : piece.toString())
                        .collect(Collectors.joining("|")))
                .collect(Collectors.joining(separatorLine));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Objects.equals(pieces, board.pieces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieces);
    }

    @Override
    public String toString() {
        return "Board{" + "pieces=" + pieces + '}';
    }

    public static Board copyFrom(Tictactoe game) {
        int size = game.getSize();

        Board copy = new Board(size);

        Iterator<Piece> iter = game.getPieces();
        IntStream.range(0, size * size)
                .forEach(i -> {
                    Piece p = iter.next();
                    if (p != null) {
                        copy.setPiece(Move.at(i), p);
                    }
                });
        return copy;
    }
}
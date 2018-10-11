package info.jayharris.tictactoe;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board {

    final int SIZE;
    private final ArrayList<Piece> pieces;

    private Board(int size) {
        SIZE = size;

        Vector<Piece> tmp = new Vector<>();
        tmp.setSize(SIZE * SIZE);
        pieces = new ArrayList<>(tmp);
    }

    Piece getPiece(int index) {
        return pieces.get(index);
    }

    private Piece getPiece(int row, int col) {
        return pieces.get(row * SIZE + col);
    }

    public void setPiece(Move move, Piece piece) {
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

    public Set<Move> legalMoves() {
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

    /**
     * Rotate the board 90° clockwise.
     */
    public void rotate() {
        Piece[] rotated = new Piece[pieces.size()];
        int s = SIZE - 1;

        for (int row = 0; row < SIZE; ++row) {
            for (int col = 0; col < SIZE; ++col) {
                rotated[col * SIZE + s - row] = getPiece(row, col);
            }
        }

        for (int i = 0; i < pieces.size(); i++) {
            pieces.set(i, rotated[i]);
        }
    }

    String pretty() {
        return prettyPrinterSupplier.get().pretty();
    }

    final Supplier<PrettyPrinter> prettyPrinterSupplier = Suppliers.memoize(PrettyPrinter::new);

    private class PrettyPrinter {

        final String separatorRow = new StringBuilder("\n")
                .append("  ")
                .append(StringUtils.join(Collections.nCopies(SIZE, "-"), '+'))
                .append("\n")
                .toString();

        final String filesRow = IntStream.range(0, SIZE)
                .mapToObj(i -> String.valueOf((char) ('a' + i)))
                .collect(Collectors.joining(" ", "\n  ", "\n"));

        String pretty() {
            Iterator<List<Piece>> partitioned = Iterators.partition(pieces.iterator(), SIZE);

            return IntStream.iterate(SIZE, x -> x - 1)
                    .limit(SIZE)
                    .mapToObj(i -> partitioned.next().stream()
                            .map(piece -> piece == null ? StringUtils.SPACE : piece.toString())
                            .collect(Collectors.joining("|", String.format("%d ", i), StringUtils.EMPTY)))
                    .collect(Collectors.joining(separatorRow, "\n", filesRow));
        }
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

    public static Board empty(int size) {
        return new Board(size);
    }

    public static Board copyFrom(Board board) {
        int size = board.SIZE;

        Board copy = new Board(size);

        Iterator<Piece> iter = board.iterator();
        IntStream.range(0, size * size)
                .forEach(i -> {
                    Piece p = iter.next();
                    if (p != null) {
                        copy.setPiece(Move.at(i), p);
                    }
                });
        return copy;
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
package info.jayharris.tictactoe;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Board implements SquareGrid {

    private final int SIZE;

    private final Piece[] pieces;

    private final ImmutableList<ImmutableList<Integer>> lines;
    private final ImmutableMap<ImmutableList<Piece>, Piece> winners;

    public Board() {
        this(3);
    }

    public Board(int size) {
        this.SIZE = size;
        pieces = new Piece[SIZE * SIZE];

        lines = Stream.of(
                IntStream.range(0, SIZE).mapToObj(this::getRowCoordsAsList),
                IntStream.range(0, SIZE).mapToObj(this::getColumnCoordsAsList),
                Stream.of(ImmutableList.copyOf(IntStream.range(0, SIZE).boxed()
                        .map(i -> SIZE * i + i)
                        .collect(Collectors.toList()))),
                Stream.of(ImmutableList.copyOf(IntStream.range(0, SIZE).boxed()
                        .map(i -> SIZE * i + (SIZE - i - 1))
                        .collect(Collectors.toList()))))
                .flatMap(Function.identity())
                .collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));

        winners = ImmutableMap.<ImmutableList<Piece>, Piece>builder()
                .put(ImmutableList.copyOf(Collections.nCopies(SIZE, Piece.X)), Piece.X)
                .put(ImmutableList.copyOf(Collections.nCopies(SIZE, Piece.O)), Piece.O)
                .build();
    }

    public Board(Board board) {
        this.SIZE = board.SIZE;
        this.pieces = Arrays.copyOf(board.pieces, SIZE * SIZE);
        this.lines = board.lines;
        this.winners = board.winners;
    }

    public Piece getPiece(int index) {
        return pieces[index];
    }

    public void setPiece(Move move, Piece piece) {
        Validate.notNull(piece);

        int index;
        Validate.isTrue(!isOccupied(index = move.getIndex()));

        pieces[index] = piece;
    }

    public boolean isOccupied(int index) {
        return Objects.nonNull(getPiece(index));
    }

    public Optional<Outcome> getOutcome() {
        List<List<Piece>> pieceLines = lines.stream()
                .map(indices -> indices.stream()
                        .map(index -> pieces[index])
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        Optional<Piece> winningPiece = pieceLines.stream()
                .filter(winners::containsKey)
                .findAny()
                .map(winners::get);

        Optional<Outcome> outcome = winningPiece.map(Outcome::new);

        if (!outcome.isPresent() && Arrays.asList(pieces).stream().allMatch(Objects::nonNull)) {
            outcome = Optional.of(Outcome.tie());
        }

        return outcome;
    }

    public int getSize() {
        return SIZE;
    }

    public int numSquares() {
        return SIZE * SIZE;
    }

    public String pretty() {
        String d = String.format("\n%s\n", IntStream.range(0, getSize()).mapToObj(i -> "-").collect(Collectors.joining("+")));

        return IntStream.range(0, getSize())
                .mapToObj(row -> IntStream.range(row * getSize(), (row + 1) * getSize())
                        .mapToObj(index -> Optional.ofNullable(pieces[index]).map(Piece::toString).orElse(StringUtils.SPACE))
                        .collect(Collectors.joining("|")))
                .collect(Collectors.joining(d));
    }

    /**
     * Gets the indices of the squares in a particular column.
     *
     * Example: on a 3 x 3 board, {@code getRowCoordsAsList(2) => [6,7,8]} since
     * squares 6, 7, and 8 comprise the second row on the board (zero-indexed).
     */
    private ImmutableList<Integer> getRowCoordsAsList(int rowNum) {
        ImmutableList.Builder<Integer> builder = ImmutableList.builder();
        IntStream.range(0, SIZE).map(i -> SIZE * rowNum + i).forEach(builder::add);
        return builder.build();
    }

    /**
     * Gets the indices of the squares in a particular column.
     *
     * Example: on a 3 x 3 board, {@code getColumnCoordsAsList(1) => [1,4,7]} since
     * squares 1, 4, and 7 comprise the first column on the board (zero-indexed).
     */
    private ImmutableList<Integer> getColumnCoordsAsList(int colNum) {
        ImmutableList.Builder<Integer> builder = ImmutableList.builder();
        IntStream.range(0, SIZE).map(i -> colNum + SIZE * i).forEach(builder::add);
        return builder.build();
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
package info.jayharris.tictactoe;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class SquareGrid implements Square {

    protected final int SIZE;
    protected final ImmutableList<ImmutableList<Integer>> lines;
    protected final ImmutableMap<ImmutableList<Piece>, Piece> winners;

    public SquareGrid(int size) {
        this.SIZE = size;

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

    public abstract Piece getPiece(int index);

    public abstract boolean isOccupied(int index);

    public int getSize() {
        return SIZE;
    }

    public int numSquares() {
        return SIZE * SIZE;
    }

    public abstract boolean isFull();

    /**
     * Gets the indices of the squares in a particular column.
     *
     * Example: on a 3 x 3 board, {@code getRowCoordsAsList(2) => [6,7,8]} since
     * squares 6, 7, and 8 comprise the second row on the board (zero-indexed).
     */
    protected ImmutableList<Integer> getRowCoordsAsList(int rowNum) {
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
    protected ImmutableList<Integer> getColumnCoordsAsList(int colNum) {
        ImmutableList.Builder<Integer> builder = ImmutableList.builder();
        IntStream.range(0, SIZE).map(i -> colNum + SIZE * i).forEach(builder::add);
        return builder.build();
    }

    public Optional<Outcome> getOutcome() {
        List<List<Piece>> pieceLines = lines.stream()
                .map(indices -> indices.stream()
                        .map(this::getPiece)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        Optional<Piece> winningPiece = pieceLines.stream()
                .filter(winners::containsKey)
                .findAny()
                .map(winners::get);

        Optional<Outcome> outcome = winningPiece.map(Outcome::new);

        if (!outcome.isPresent() && isFull()) {
            outcome = Optional.of(Outcome.tie());
        }

        return outcome;
    }
}

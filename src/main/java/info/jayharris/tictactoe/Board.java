package info.jayharris.tictactoe;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Board implements SquareGrid {

    private final int SIZE;

    private final Piece[] pieces;
    
    private final List<List<Integer>> lines;
    private final Map<List<Piece>, Piece> winners;

    public Board() {
        this(3);
    }

    public Board(int size) {
        this.SIZE = size;
        pieces = new Piece[SIZE * SIZE];

        Function<Integer, List<Integer>> eachRow = (row) -> IntStream.range(0, SIZE).boxed()
                .map(i -> SIZE * row + i)
                .collect(Collectors.toList());
        Function<Integer, List<Integer>> eachCol = (col) -> IntStream.range(0, SIZE).boxed()
                .map(i -> col + SIZE * i)
                .collect(Collectors.toList());
        Function<Piece, List<Piece>> listOfPieces = (piece) -> IntStream.range(0, SIZE).boxed()
                .map(i -> piece)
                .collect(Collectors.toList());


        lines = Stream.of(
                IntStream.range(0, SIZE).boxed().map(eachRow),
                IntStream.range(0, SIZE).boxed().map(eachCol),
                Stream.of(IntStream.range(0, SIZE).boxed()
                        .map(i -> SIZE * i + i)
                        .collect(Collectors.toList())),
                Stream.of(IntStream.range(0, SIZE).boxed()
                        .map(i -> SIZE * i + (SIZE - i - 1))
                        .collect(Collectors.toList())))
                .flatMap(Function.identity())
                .collect(Collectors.toList());

        winners = Stream.of(Piece.X, Piece.O)
                .collect(Collectors.toMap(listOfPieces, Function.identity()));
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
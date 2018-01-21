package info.jayharris.tictactoe;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Board {

    public final int SIZE;

    final List<Piece> squares;
    final Stream<List<Pair<Integer, Integer>>> lines;

    public Board() {
        this(3);
    }

    public Board(int size) {
        this.SIZE = size;
        squares = new ArrayList<Piece>(Arrays.asList(new Piece[SIZE * SIZE]));

        lines = Stream.of(
                IntStream.range(0, SIZE).mapToObj(row -> IntStream.range(0, SIZE).mapToObj(i -> Pair.of(row, i)).collect(Collectors.toList())),
                IntStream.range(0, SIZE).mapToObj(col -> IntStream.range(0, SIZE).mapToObj(i -> Pair.of(i, col)).collect(Collectors.toList())),
                Stream.of(IntStream.range(0, SIZE).mapToObj(i -> Pair.of(i, i)).collect(Collectors.toList())),
                Stream.of(IntStream.range(0, SIZE).mapToObj(i -> Pair.of(SIZE - i, i)).collect(Collectors.toList())))
                .flatMap(Function.identity());
    }

    public Piece getPiece(Pair<Integer, Integer> square) {
        return squares.get(square.getLeft() * SIZE + square.getRight());
    }

    public void setPiece(Pair<Integer, Integer> square, Piece piece) {
        int index = square.getLeft() * SIZE + square.getRight();

        Validate.isTrue(Objects.isNull(squares.get(index)));

        squares.set(index, piece);
    }

    /**
     * Determines the game's outcome, if the game is over.
     *
     * @return the {@code Outcome} if the game is over, or {@code null} otherwise.
     */
    public Outcome winner() {



        lines.map(this::getPiece).map(Board::allSame).filter(Objects::nonNull).findFirst();

        if (squares.stream().allMatch(Objects::nonNull)) {
            return Outcome.tie();
        }

        return null;
    }

    private Piece foo(List<Pair<Integer, Integer>> r) {
        List<Piece> pieces = r.stream().map(this::getPiece).collect(Collectors.toList());

        return Board.allSame(pieces);
    }

    private boolean full() {
        return squares.stream().allMatch(Objects::nonNull);
    }

    private static Piece allSame(List<Piece> pieces) {
        Piece first = null;

        for (Piece piece : pieces) {
            if (Objects.isNull(piece)) {
                return null;
            }

            if (first == null) {
                first = piece;
            }
            else if (piece != first) {
                return null;
            }
        }
        return first;
    }

    private static Piece allSame(Stream<Piece> pieces) {
        Piece identity = pieces.findFirst().get();


    }

    public String pretty() {
        String d = String.format("\n%s\n", IntStream.range(0, SIZE).mapToObj(i -> "-").collect(Collectors.joining("+")));

        return IntStream.range(0, SIZE).mapToObj(row -> IntStream.range(row * SIZE, (row + 1) * SIZE).mapToObj(index -> {
            Piece p = squares.get(index);
            return p.map(Piece::toString).orElse(StringUtils.SPACE);
        }).collect(Collectors.joining("|"))).collect(Collectors.joining(d));
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Board{");
        sb.append("squares=").append(squares == null ? "null" : Arrays.asList(squares).toString());
        sb.append('}');
        return sb.toString();
    }

    public static void main(String... args) {
        Board b = new Board();

        b.setPiece(Pair.of(0,1), Piece.X);
        b.setPiece(Pair.of(1,2), Piece.O);

        System.out.println(b);
        System.out.println(b.pretty());
    }
}
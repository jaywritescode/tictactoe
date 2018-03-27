package info.jayharris.tictactoe.minimax;

import com.google.common.collect.Lists;
import info.jayharris.minimax.State;
import info.jayharris.tictactoe.Move;
import info.jayharris.tictactoe.Piece;
import info.jayharris.tictactoe.SquareGrid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MinimaxState extends SquareGrid implements State<MinimaxState, MinimaxAction> {

    private final ArrayList<Piece> board;
    private final Piece toMove;

    public MinimaxState(int size, Iterator<Piece> pieces, Piece toMove) {
        super(size);
        this.board = Lists.newArrayList(pieces);
        this.toMove = toMove;
    }

    public MinimaxState(MinimaxState predecessor, Move move) {
        super(predecessor.getSize());

        this.board = new ArrayList<>();
        board.addAll(predecessor.board);
        board.set(move.getIndex(), predecessor.getToMove());

        this.toMove = predecessor.getToMove().opposite();
    }

    public Piece getToMove() {
        return toMove;
    }

    @Override
    public Piece getPiece(int index) {
        return board.get(index);
    }

    @Override
    public boolean isOccupied(int index) {
        return Objects.nonNull(getPiece(index));
    }

    @Override
    public boolean isFull() {
        return board.stream().allMatch(Objects::nonNull);
    }

    @Override
    public Collection<MinimaxAction> actions() {
        IntPredicate isOccupied = this::isOccupied;

        return IntStream.range(0, numSquares())
                .filter(isOccupied.negate())
                .mapToObj(MinimaxAction::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean terminalTest() {
        return getOutcome().isPresent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MinimaxState that = (MinimaxState) o;
        return Objects.equals(board, that.board) &&
               toMove == that.toMove;
    }

    @Override
    public int hashCode() {
        return Objects.hash(board, toMove);
    }
}

package info.jayharris.tictactoe.minimax;

import info.jayharris.minimax.State;
import info.jayharris.tictactoe.Board;
import info.jayharris.tictactoe.Outcome;
import info.jayharris.tictactoe.Piece;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MinimaxState implements State<MinimaxState, Long> {

    private final Board board;
    private final Piece toMove;

    private final IntPredicate isOccupied;

    public MinimaxState(Board board, Piece toMove) {
        this.board = board;
        this.toMove = toMove;

        this.isOccupied = board::isOccupied;
    }

    public Board getBoardCopy() {
        return new Board(board);
    }

    public Piece getToMove() {
        return toMove;
    }

    public Optional<Outcome> getOutcome() {
        return board.getOutcome();
    }

    @Override
    public Collection<MinimaxAction> actions() {
        return IntStream.range(0, board.numSquares())
                .filter(isOccupied.negate())
                .mapToObj(MinimaxAction::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean terminalTest() {
        return getOutcome().isPresent();
    }

    @Override
    public Long utility() {
        return null;
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

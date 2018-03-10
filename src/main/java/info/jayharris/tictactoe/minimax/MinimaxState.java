package info.jayharris.tictactoe.minimax;

import info.jayharris.minimax.State;
import info.jayharris.minimax.Utility;
import info.jayharris.tictactoe.Board;
import info.jayharris.tictactoe.Piece;

import java.util.Collection;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MinimaxState implements State<MinimaxState> {

    private final Board board;
    private final Piece toMove;

    private final IntPredicate isOccupied;

    public MinimaxState(Board board, Piece toMove) {
        this.board = board;
        this.toMove = toMove;

        this.isOccupied = board::isOccupied;
    }

    public Board getBoard() {
        return board;
    }

    public Piece getToMove() {
        return toMove;
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
        return board.getOutcome().isPresent();
    }

    @Override
    public <T extends Comparable<T>, U extends Utility<T>> U utility() {
        return null;
    }


}
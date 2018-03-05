package info.jayharris.tictactoe.minimax;

import info.jayharris.minimax.Action;
import info.jayharris.minimax.State;
import info.jayharris.minimax.Utility;
import info.jayharris.tictactoe.Board;
import info.jayharris.tictactoe.Piece;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MinimaxState implements State<MinimaxState> {

    private final Board board;
    private final Piece toMove;

    public MinimaxState(Board board, Piece toMove) {
        this.board = board;
        this.toMove = toMove;
    }

    public Board getBoard() {
        return board;
    }

    public Piece getToMove() {
        return toMove;
    }

    @Override
    public Collection<? extends Action<MinimaxState>> actions() {
        return IntStream.range(0, board.getSize())
                .filter(it -> board.isOccupied(it))
                .mapToObj(MinimaxAction::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean terminalTest() {
        return false;
    }

    @Override
    public <T extends Comparable<T>> Utility<T> utility() {
        return null;
    }
}

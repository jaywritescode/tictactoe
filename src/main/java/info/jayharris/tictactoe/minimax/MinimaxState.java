package info.jayharris.tictactoe.minimax;

import info.jayharris.minimax.State;
import info.jayharris.tictactoe.Board;
import info.jayharris.tictactoe.Piece;
import info.jayharris.tictactoe.TictactoeUtils;

import java.util.Collection;
import java.util.stream.Collectors;

public class MinimaxState implements State<MinimaxState, MinimaxAction> {

    private final Board board;
    private final Piece toMove;

    public MinimaxState(Board board, Piece toMove) {
        this.board = board;
        this.toMove = toMove;
    }

    @Override
    public Collection<MinimaxAction> actions() {
        return board.legalMoves().stream()
                .map(MinimaxAction::create)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean terminalTest() {
        return TictactoeUtils.getOutcome(board).isPresent();
    }

    public Board getBoard() {
        return board;
    }

    Piece getToMove() {
        return toMove;
    }
}

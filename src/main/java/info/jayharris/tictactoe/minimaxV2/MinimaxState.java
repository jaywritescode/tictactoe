package info.jayharris.tictactoe.minimaxV2;

import info.jayharris.minimax.BaseState;
import info.jayharris.tictactoe.Board;
import info.jayharris.tictactoe.Piece;
import info.jayharris.tictactoe.Tictactoe;

import java.util.Collection;
import java.util.OptionalLong;
import java.util.stream.Collectors;

public class MinimaxState extends BaseState<MinimaxState, MinimaxAction> {

    Board board;
    Piece nextPiece;

    MinimaxState(Board board, Piece nextPiece) {
        this.board = board;
        this.nextPiece = nextPiece;
    }

    @Override
    public Collection<MinimaxAction> actions() {
        return board.legalMoves().stream()
                .map(MinimaxAction::from)
                .collect(Collectors.toList());
    }

    @Override
    public OptionalLong utility() {
        return null;
    }

    public Board getBoard() {
        return board;
    }

    public Piece getNextPiece() {
        return nextPiece;
    }

    public static MinimaxState of(Tictactoe game) {
        Board board = Board.copyFrom(game);
        Piece nextPiece = game.getCurrentPlayer().piece;

        return new MinimaxState(board, nextPiece);
    }

    protected static MinimaxState of(Board board, Piece nextPiece) {
        return new MinimaxState(board, nextPiece);
    }
}

package info.jayharris.tictactoe.minimaxV2;

import info.jayharris.minimax.State;
import info.jayharris.tictactoe.Board;
import info.jayharris.tictactoe.Piece;
import info.jayharris.tictactoe.Tictactoe;
import info.jayharris.tictactoe.player.Player;

import java.util.Collection;
import java.util.OptionalLong;

public class MinimaxState implements State<MinimaxState, MinimaxAction> {

    Board board;
    Piece nextPiece;

    MinimaxState(Board board, Piece nextPiece) {
        this.board = board;
        this.nextPiece = nextPiece;
    }

    @Override
    public Collection<MinimaxAction> actions() {
        return null;
    }

    @Override
    public OptionalLong utility() {
        return null;
    }

    @Override
    public boolean terminalTest() {
        return false;
    }

    public static MinimaxState fromGame(Tictactoe game) {
        Board board = Board.copyFrom(game);
        Piece nextPiece = game.getCurrentPlayer().piece;

        return new MinimaxState(board, nextPiece);
    }
}

package info.jayharris.tictactoe.minimaxV2;

import info.jayharris.minimax.BaseState;
import info.jayharris.tictactoe.*;
import info.jayharris.tictactoe.player.Player;

import java.util.Collection;
import java.util.OptionalLong;
import java.util.stream.Collectors;

public class MinimaxState extends BaseState<MinimaxState, MinimaxAction> {

    Board board;
    Piece nextPiece;
    Piece playerPiece;

    MinimaxState(Board board, Piece nextPiece, Piece playerPiece) {
        this.board = board;
        this.nextPiece = nextPiece;
        this.playerPiece = playerPiece;
    }

    @Override
    public Collection<MinimaxAction> actions() {
        return board.legalMoves().stream()
                .map(MinimaxAction::from)
                .collect(Collectors.toList());
    }

    @Override
    public OptionalLong utility() {
        if (!actions().isEmpty()) {
            return OptionalLong.empty();
        }

        // TODO: throw a more informative exception
        Outcome outcome = TictactoeUtils.getOutcome(board).orElseThrow(RuntimeException::new);

        if (outcome.isTie()) {
            return OptionalLong.of(0L);
        }

        return OptionalLong.of(outcome.winner().equals(playerPiece) ? 1 : -1);
    }

    public Board getBoard() {
        return board;
    }

    public Piece getNextPiece() {
        return nextPiece;
    }

    public static MinimaxState of(Tictactoe game, Player player) {
        Board board = Board.copyFrom(game);
        Piece nextPiece = game.getCurrentPlayer().getPiece();

        return new MinimaxState(board, nextPiece, player.getPiece());
    }

    protected static MinimaxState of(Board board, Piece nextPiece, Piece playerPiece) {
        return new MinimaxState(board, nextPiece, playerPiece);
    }
}

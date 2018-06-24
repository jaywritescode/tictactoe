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

    final static long WIN = 1, TIE = 0, LOSS = -1;

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
        if (!terminalTest()) {
            return OptionalLong.empty();
        }

        // TODO: throw a more informative exception
        Outcome outcome = TictactoeUtils.getOutcome(board).orElseThrow(RuntimeException::new);

        if (outcome.isTie()) {
            return OptionalLong.of(TIE);
        }

        return OptionalLong.of(outcome.winner().equals(playerPiece) ? WIN : LOSS);
    }

    @Override
    public boolean terminalTest() {
        return super.terminalTest() || TictactoeUtils.isGameOver(board);
    }

    public Board getBoard() {
        return board;
    }

    public Piece getNextPiece() {
        return nextPiece;
    }

    public MinimaxState successor(Board board) {
        return MinimaxState.of(board, nextPiece.opposite(), playerPiece);
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

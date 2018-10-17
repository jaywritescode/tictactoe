package info.jayharris.tictactoe.minimax;

import info.jayharris.minimax.BaseState;
import info.jayharris.minimax.Node;
import info.jayharris.tictactoe.*;
import info.jayharris.tictactoe.player.Player;

import java.util.Collection;
import java.util.stream.Collectors;

public class MinimaxState extends BaseState<MinimaxState, MinimaxAction> {

    private final Board board;
    private final Piece nextPiece;
    private final Piece playerPiece;

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
    public double eval() {
        Outcome outcome = TictactoeUtils.getOutcome(board)
                .orElseThrow(() -> new IllegalStateException(
                        "Error: for tictactoe, eval should only be called on terminal nodes."));

        if (outcome.isTie()) {
            return TIE;
        }

        return outcome.winner().equals(playerPiece) ? WIN : LOSS;
    }

    @Override
    public boolean terminalTest() {
        return TictactoeUtils.hasWinner(board);
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

    /**
     * Create a MinimaxState on the current state of the game, assuming that Player
     * is next to move.
     *
     * @param game
     * @param player
     * @return
     */
    public static Node<MinimaxState, MinimaxAction> root(Tictactoe game, Player player) {
        Board board = Board.copyFrom(game);
        Piece piece = player.getPiece();

        return Node.root(MinimaxState.of(board, piece, piece));
    }

    protected static MinimaxState of(Board board, Piece nextPiece, Piece playerPiece) {
        return new MinimaxState(board, nextPiece, playerPiece);
    }
}

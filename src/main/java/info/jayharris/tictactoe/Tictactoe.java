package info.jayharris.tictactoe;

import info.jayharris.tictactoe.player.MinimaxPlayer;
import info.jayharris.tictactoe.player.Player;
import info.jayharris.tictactoe.player.TerminalPlayer;

import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public class Tictactoe {

    private final Player x, o;
    private Player current;
    private final Board board;
    private int ply;

    public Tictactoe(Player x, Player o) {
        this(x, o, 3);
    }

    Tictactoe(Player x, Player o, int size) {
        this(x, o, Board.empty(size));
    }

    Tictactoe(Player x, Player o, Board board) {
        this.current = this.x = x;
        this.o = o;
        this.board = board;

        this.ply = 0;
    }

    Outcome play() {
        Optional<Outcome> winner;
        while (!(winner = nextPly()).isPresent()) {
            current = (current == x ? o : x);
        }

        return winner.get();
    }

    public Player getCurrentPlayer() {
        return current;
    }

    public int getPly() {
        return ply;
    }

    public int getSize() {
        return board.SIZE;
    }

    public String pretty() {
        return board.pretty();
    }

    public Iterator<Piece> getPieces() {
        return board.iterator();
    }

    public Set<Move> getLegalMoves() {
        return board.legalMoves();
    }

    /**
     * Plays the next ply of the game.
     *
     * @return the outcome if the game is over, otherwise null
     */
    private Optional<Outcome> nextPly() {
        ++ply;
        current.begin(this);

        while (true) {
            try {
                board.setPiece(current.getMove(this), current.getPiece());
                current.end(this);
                return TictactoeUtils.getOutcome(board);
            }
            catch (IllegalArgumentException e) {
                current.fail(this, e);
            }
        }
    }

    public static void main(String... args) {
        Tictactoe game = new Tictactoe(new TerminalPlayer(Piece.X), new MinimaxPlayer(Piece.O));

        Outcome outcome = game.play();

        System.out.println(game.pretty());
        if (outcome.isTie()) {
            System.out.println("It's a tie!");
        }
        else {
            System.out.println(String.format("%s wins!", outcome.winner()));
        }
    }
}

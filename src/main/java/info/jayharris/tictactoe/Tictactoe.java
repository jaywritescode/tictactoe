package info.jayharris.tictactoe;

import info.jayharris.tictactoe.player.Player;
import info.jayharris.tictactoe.player.TerminalPlayer;
import org.apache.commons.lang3.Validate;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Tictactoe {

    private final Player x, o;
    private Player current;
    private final Board board;
    private int ply;

    public Tictactoe(Player x, Player o) {
        this(x, o, 3);
    }

    Tictactoe(Player x, Player o, int size) {
        this.board = new Board(size);
        this.current = this.x = x;
        this.o = o;

        this.ply = 0;

    }

    Outcome play() {
        Optional<Outcome> winner;
        while (!(winner = nextPly()).isPresent()) {
            current = (current == x ? o : x);
        }

        return winner.get();
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

    /**
     * Plays the next ply of the game.
     *
     * @return the outcome if the game is over, otherwise null
     */
    private Optional<Outcome> nextPly() {
        ++ply;
        current.begin(this);

        try {
            board.setPiece(current.getMove(this), current.piece);
            current.end(this);
        }
        catch (IllegalArgumentException e) {
            current.fail(this, e);
        }

        return getOutcome();
    }

    private Optional<Outcome> getOutcome() {
        return Optional.ofNullable(
                board.getAllTicTacToeLines()
                .stream()
                .filter(Tictactoe::isWinningLine)
                .findFirst()
                .map(Tictactoe::firstPiece)
                .map(Outcome::new)
                .orElse(board.isFull() ? Outcome.tie() : null));
    }

    private static Piece firstPiece(List<Piece> pieces) {
        Validate.notEmpty(pieces);
        return Validate.notNull(pieces.get(0));
    }

    private static boolean isWinningLine(List<Piece> pieces) {
        Validate.notEmpty(pieces);

        Piece first = pieces.get(0);
        return first != null && pieces.stream().allMatch(Predicate.isEqual(first));
    }

    public static void main(String... args) {
        Tictactoe game = new Tictactoe(new TerminalPlayer(Piece.X), new TerminalPlayer(Piece.O));

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

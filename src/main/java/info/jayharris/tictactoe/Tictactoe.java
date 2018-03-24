package info.jayharris.tictactoe;

import info.jayharris.tictactoe.player.Player;
import info.jayharris.tictactoe.player.TerminalPlayer;

import java.util.Optional;

public class Tictactoe implements SquareGrid {

    private final Player x, o;
    private Player current;
    private final Board board;
    private int ply;

    public Tictactoe(Player x, Player o) {
        this.board = new Board();
        this.current = this.x = x;
        this.o = o;

        this.ply = 0;
    }

    public Outcome play() {
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
        return board.getSize();
    }

    public String pretty() {
        return board.pretty();
    }

    /**
     *
     * @return a copy of the board
     */
    public Board copyBoard() {
        return new Board(this.board);
    }

    /**
     * Plays the next ply of the game.
     *
     * @return the outcome if the game is over, otherwise null
     */
    private Optional<Outcome> nextPly() {
        ++ply;
        current.begin(this);

        while(true) {
            try {
                board.setPiece(current.getMove(this), current.piece);
                current.end(this);
            }
            catch (IllegalArgumentException e) {
                current.fail(this, e);
            }

            return board.getOutcome();
        }
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

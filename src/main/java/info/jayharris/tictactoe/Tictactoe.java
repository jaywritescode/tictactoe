package info.jayharris.tictactoe;

import com.beust.jcommander.IStringConverter;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
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

    /**
     * Construct a new Tictactoe game.
     *
     * @param x the X player
     * @param o the O player
     */
    public Tictactoe(Player x, Player o) {
        this(x, o, 3);
    }

    /**
     * Construct a new Tictactoe game.
     *
     * @param x the X player
     * @param o the O player
     * @param size the width (or height) of the board, in squares
     */
    public Tictactoe(Player x, Player o, int size) {
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

    /**
     * Gets the current ply. Before the first move, the current ply is 0.
     *
     * @return the current ply
     */
    public int getPly() {
        return ply;
    }

    /**
     * Gets the width (or height) of the board, in squares.
     *
     * @return the board size
     */
    public int getSize() {
        return board.SIZE;
    }

    /**
     * Gets a pretty-printable string displaying the game.
     *
     * @return a pretty-printable string
     */
    public String pretty() {
        return board.pretty();
    }

    Iterator<Piece> getPieces() {
        return board.iterator();
    }

    /**
     * Determines if {@code move} is legal at this point in the game.
     *
     * @param move the move
     * @return true iff {@code move} is legal
     */
    public boolean isLegalMove(Move move) {
        return !board.isOccupied(move.getIndex());
    }

    /**
     * Gets a collection of legal moves at this point in the game.
     *
     * @return a set of legal moves
     */
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

    public static void main(String... args) throws Exception {
        class ClassnameConverter implements IStringConverter<Class<?>> {
            @Override
            public Class<?> convert(String value) {
                try {
                    return Class.forName("info.jayharris.tictactoe.player." + value);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }

        class Args {
            @Parameter(names = {"--size"}, description = "Board size")
            private int size = 3;

            @Parameter(
                    names = {"--playerX"},
                    description = "Type for Player X",
                    converter = ClassnameConverter.class
            )
            private Class<? extends Player> playerX = TerminalPlayer.class;

            @Parameter(
                    names = {"--playerO"},
                    description = "Type for Player O",
                    converter = ClassnameConverter.class
            )
            private Class<? extends Player> playerO = MinimaxPlayer.class;

            @Parameter(names = {"--redis"}, description = "Use Redis for transposition table (not implemented)")
            private boolean useRedis = false;
        }

        Args parameters = new Args();
        JCommander.newBuilder()
                .addObject(parameters)
                .build()
                .parse(args);

        Tictactoe game = new Tictactoe(
                parameters.playerX.getConstructor(Piece.class).newInstance(Piece.X),
                parameters.playerO.getConstructor(Piece.class).newInstance(Piece.O),
                parameters.size);

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

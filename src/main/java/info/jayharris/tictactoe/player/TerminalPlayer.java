package info.jayharris.tictactoe.player;

import info.jayharris.tictactoe.Move;
import info.jayharris.tictactoe.Piece;
import info.jayharris.tictactoe.Tictactoe;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class TerminalPlayer extends Player {

    enum MoveState {
        IDLE, ACTIVE
    }

    private MoveState state = MoveState.IDLE;

    private final BufferedReader reader;
    private final PrintStream out;

    private final static String PLAYER_TO_MOVE_MSG_TPL = "Ply %s >> %s to play: \n",
            ILLEGAL_MOVE_MSG = "Illegal move!",
            INVALID_MSG_TPL = "%s is invalid algebraic notation. Try again: ";

    public TerminalPlayer(Piece piece) {
        this(piece, System.out);
    }

    public TerminalPlayer(Piece piece, PrintStream out) {
        this(piece, new BufferedReader(new InputStreamReader(System.in)), out);
    }

    public TerminalPlayer(Piece piece, BufferedReader reader, PrintStream out) {
        super(piece);
        this.reader = reader;
        this.out = out;
    }

    @Override
    public Move getMove(Tictactoe game) {
        out.print(String.format(PLAYER_TO_MOVE_MSG_TPL, game.getPly(), getPiece().toString()));

        try {
            String line;
            while (true) {
                line = reader.readLine();

                try {
                    return getLegalMove(line.toLowerCase(), game);
                } catch (IllegalArgumentException e) {
                    out.println(String.format(INVALID_MSG_TPL, line));
                }
            }
        } catch (IOException e) {
            // TODO: handle this better
            throw new RuntimeException(e);
        }
    }

    @Override
    public void begin(Tictactoe game) {
        out.println(state == MoveState.IDLE ? game.pretty() : ILLEGAL_MOVE_MSG);
        state = MoveState.ACTIVE;
    }

    @Override
    public void end(Tictactoe game) {
        state = MoveState.IDLE;
    }

    @Override
    public void fail(Tictactoe game, Exception e) {
        out.println(ILLEGAL_MOVE_MSG);
    }

    private static Move getLegalMove(String string, Tictactoe game) {
        Validate.isTrue(string.length() >= 2, "Algebraic notation must have length of two or more.");

        int size = game.getSize();

        int file = string.charAt(0) - 'a';
        Validate.isTrue(file >= 0 && file < size, "File must be between 'a' and '%s', inclusive", (char) ('a' + size - 1));

        int rank = NumberUtils.toInt(string.substring(1));
        Validate.isTrue(rank > 0 && rank <= size, "Rank must be an integer between 1 and %d, inclusive", size);

        int row = size - rank, column = file;

        Move possibleMove = Move.at(row * size + column);

        Validate.isTrue(game.isLegalMove(possibleMove));

        return possibleMove;
    }
}

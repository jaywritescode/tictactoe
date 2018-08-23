package info.jayharris.tictactoe.player;

import info.jayharris.tictactoe.Move;
import info.jayharris.tictactoe.Piece;
import info.jayharris.tictactoe.Tictactoe;
import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

        String highestRank = String.valueOf(game.getSize());
        String highestFile = String.valueOf((char) ('a' + game.getSize() - 1));
        String regex = String.format("[a-%s][1-%s]", highestFile, highestRank);

        Pattern pattern = Pattern.compile(regex);

        try {
            String line;
            while (true) {
                if (pattern.matcher(line = reader.readLine()).matches()) {
                    return Move.at(algebraicNotationToIndex(line, game));
                }

                out.println(String.format(INVALID_MSG_TPL, line));
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int algebraicNotationToIndex(String algebraicNotation, Tictactoe game) {
        String file = algebraicNotation.substring(0, 1);
        String rank = algebraicNotation.substring(1, 2);

        return rankToIndex(rank) * game.getSize() + fileToIndex(file);
    }

    private int rankToIndex(String rank) {
        return Integer.valueOf(rank) - 1;
    }

    private int fileToIndex(String file) {
        return file.toLowerCase().charAt(0) - 'a';
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
}

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
    };
    MoveState state = MoveState.IDLE;

    BufferedReader reader;
    final PrintStream out;

    final static Pattern PATTERN = Pattern.compile("\\d+,\\d+");
    final static String PLAYER_TO_MOVE_MSG_TPL = "Ply %s >> %s to play: ",
            ILLEGAL_MOVE_MSG = "Illegal move!",
            INVALID_MSG_TPL = "%s is invalid algebraic notation. Try again: ";

    public TerminalPlayer(Piece piece) {
        this(piece, System.out);
    }

    public TerminalPlayer(Piece piece, PrintStream out) {
        super(piece);
        this.out = out;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public Move getMove(Tictactoe game) {
        out.print(String.format(PLAYER_TO_MOVE_MSG_TPL, game.getPly(), piece.toString()));

        String line;
        try {
            while (true) {
                if (PATTERN.matcher(line = reader.readLine()).matches()) {
                    List<Integer> coords = Arrays.asList(line.split(",")).stream()
                            .mapToInt(Integer::valueOf)
                            .boxed()
                            .collect(Collectors.toList());

                    if (coords.stream().allMatch(i -> i < game.getSize())) {
                        return new Move(game, Pair.of(coords.get(0), coords.get(1)));
                    }
                }

                out.println(String.format(INVALID_MSG_TPL, line));
            }
        }
        catch (IOException e) {
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
}

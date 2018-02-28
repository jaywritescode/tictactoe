package info.jayharris.tictactoe;

import info.jayharris.tictactoe.player.Player;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


class TictactoeTest {

    private static Field boardField;

    @BeforeAll
    static void setUp() throws Exception {
        boardField = Tictactoe.class.getDeclaredField("board");
        boardField.setAccessible(true);
    }

    @Test
    @DisplayName("run the entire game")
    void play() {
        List<Move> moves = Arrays.asList(
                Pair.of(1, 1),
                Pair.of(0, 0),
                Pair.of(2, 0),
                Pair.of(0, 2),
                Pair.of(0, 1),
                Pair.of(2, 1),
                Pair.of(1, 0),
                Pair.of(1, 2),
                Pair.of(2, 2)).stream()
                .map(pair -> pair.getLeft() * 3 + pair.getRight())
                .map(Move::new)
                .collect(Collectors.toList());
        Iterator<Move> iter = moves.iterator();

        Player x = new Player(Piece.X) {
            @Override
            public Move getMove(Tictactoe game) {
                return iter.next();
            }
        };
        Player o = new Player(Piece.O) {
            @Override
            public Move getMove(Tictactoe game) {
                return iter.next();
            }
        };

        assertThat(new Tictactoe(x, o).play()).isEqualTo(Outcome.tie());
    }
}
package info.jayharris.tictactoe;

import info.jayharris.tictactoe.player.Player;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


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
        List<Pair<Integer, Integer>> moves = Arrays.asList(
                Pair.of(1,1),
                Pair.of(0,0),
                Pair.of(2,0),
                Pair.of(0,2),
                Pair.of(0,1),
                Pair.of(2,1),
                Pair.of(1,0),
                Pair.of(1,2),
                Pair.of(2,2)
        );
        Iterator<Pair<Integer, Integer>> iter = moves.iterator();
        
        Player x = new Player(Piece.X) {
            @Override
            public Pair<Integer, Integer> getMove(Tictactoe game) {
                return iter.next();
            }
        };
        Player o = new Player(Piece.O) {
            @Override
            public Pair<Integer, Integer> getMove(Tictactoe game) {
                return iter.next();
            }
        };

        assertThat(new Tictactoe(x, o).play()).isEqualTo(Outcome.tie());
    }
}
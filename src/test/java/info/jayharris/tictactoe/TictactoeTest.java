package info.jayharris.tictactoe;

import info.jayharris.tictactoe.player.Player;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.*;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


class TictactoeTest {

    @Nested
    @DisplayName("run the entire game")
    class Play {

        Player x, o;
        Tictactoe game;

        MovesSupplier movesSupplier;

        @BeforeEach
        void setUp() {
            x = new Player(Piece.X) {
                @Override
                public Move getMove(Tictactoe game) {
                    return movesSupplier.get();
                }
            };
            o = new Player(Piece.O) {
                @Override
                public Move getMove(Tictactoe game) {
                    return movesSupplier.get();
                }
            };

            game = new Tictactoe(x, o, 4);
        }

        @Test
        @DisplayName("with winner - horizontal")
        void testWithWinnerHorizontal() {
            movesSupplier = new MovesSupplier(Stream.of(
                    Pair.of(1,1),
                    Pair.of(2,2),
                    Pair.of(0,1),
                    Pair.of(0,2),
                    Pair.of(1,0),
                    Pair.of(2,0),
                    Pair.of(1,2),
                    Pair.of(3,0),
                    Pair.of(2,1),
                    Pair.of(1,3),
                    Pair.of(3,1))
                    .map(toMove(game.getSize()))
                    .iterator());

            assertThat(game.play()).isEqualTo(Outcome.x());
        }

        @Test
        @DisplayName("with winner - vertical")
        void testWithWinnerVertical() {
            movesSupplier = new MovesSupplier(Stream.of(
                    Pair.of(0,1),
                    Pair.of(3,2),
                    Pair.of(1,2),
                    Pair.of(2,2),
                    Pair.of(1,3),
                    Pair.of(2,1),
                    Pair.of(3,1),
                    Pair.of(2,3),
                    Pair.of(0,3),
                    Pair.of(0,2),
                    Pair.of(0,0),
                    Pair.of(2,0))
                    .map(toMove(game.getSize()))
                    .iterator());

            assertThat(game.play()).isEqualTo(Outcome.o());
        }

        @Test
        @DisplayName("with winner - diagonal upper-left to lower right")
        void testWithWinnerUpperLeftToLowerRight() {
            movesSupplier = new MovesSupplier(Stream.of(
                    Pair.of(0,0),
                    Pair.of(2,0),
                    Pair.of(1,1),
                    Pair.of(2,1),
                    Pair.of(2,2),
                    Pair.of(3,0),
                    Pair.of(3,3))
                    .map(toMove(game.getSize()))
                    .iterator());

            assertThat(game.play()).isEqualTo(Outcome.x());
        }

        @Test
        @DisplayName("with winner - diagonal upper-right to lower-left")
        void testWithWinnerUpperRightToLowerLeft() {
            movesSupplier = new MovesSupplier(Stream.of(
                    Pair.of(1,1),
                    Pair.of(2,1),
                    Pair.of(2,2),
                    Pair.of(1,2),
                    Pair.of(1,3),
                    Pair.of(0,3),
                    Pair.of(3,3),
                    Pair.of(0,0),
                    Pair.of(0,1),
                    Pair.of(3,0))
                    .map(toMove(game.getSize()))
                    .iterator());

            assertThat(game.play()).isEqualTo(Outcome.o());
        }

        @Test
        @DisplayName("tie game")
        void testTieGame() {
            movesSupplier = new MovesSupplier(Stream.of(
                    Pair.of(2,1),
                    Pair.of(1,1),
                    Pair.of(1,2),
                    Pair.of(3,0),
                    Pair.of(2,2),
                    Pair.of(3,2),
                    Pair.of(2,0),
                    Pair.of(2,3),
                    Pair.of(0,2),
                    Pair.of(3,3),
                    Pair.of(3,1),
                    Pair.of(0,3),
                    Pair.of(1,3),
                    Pair.of(1,0),
                    Pair.of(0,1),
                    Pair.of(0,0))
                    .map(toMove(game.getSize()))
                    .iterator());

            assertThat(game.play()).isEqualTo(Outcome.tie());
        }
    }

    private Function<Pair<Integer, Integer>, Move> toMove(int size) {
        return pair -> Move.at(pair.getRight() * size + pair.getLeft());
    }

    class MovesSupplier implements Supplier<Move> {

        Iterator<Move> moves;

        MovesSupplier(Iterator<Move> moves) {
            this.moves = moves;
        }

        @Override
        public Move get() {
            return moves.next();
        }
    }
}
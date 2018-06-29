package info.jayharris.tictactoe.player;

import info.jayharris.tictactoe.Move;
import info.jayharris.tictactoe.Piece;
import info.jayharris.tictactoe.Tictactoe;
import info.jayharris.tictactoe.TictactoeBuilder;
import info.jayharris.tictactoe.minimax.MinimaxState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MinimaxPlayerTest {

    @Test
    @DisplayName("it chooses a winning move over a tie")
    void getMove_tie() {
        MinimaxPlayer player = new MinimaxPlayer(Piece.X);

        Tictactoe game = new TictactoeBuilder()
                .setAllMoves(Move.at(4), Move.at(2), Move.at(6), Move.at(3))
                .setPlayer(player)
                .build();

        Move result = player.getMove(game);

        assertThat(result).isIn(Move.at(7), Move.at(8));
    }

    @Test
    @DisplayName("it chooses a tie over a losing move")
    void getMove_lose() {
        MinimaxPlayer player = new MinimaxPlayer(Piece.O);

        Tictactoe game = new TictactoeBuilder()
                .setAllMoves(Move.at(4))
                .setPlayer(player)
                .build();

        Move result = player.getMove(game);

        assertThat(result).isIn(Move.at(0), Move.at(2), Move.at(6), Move.at(8));
    }

}